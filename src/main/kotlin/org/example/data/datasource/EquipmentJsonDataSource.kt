package org.example.data.datasource

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.example.domain.model.Equipment
import java.io.File
import java.io.IOException
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.StandardCopyOption.ATOMIC_MOVE
import java.nio.file.StandardCopyOption.REPLACE_EXISTING

class EquipmentJsonDataSource(
    private val file: File = File("data/equipment.json")
) {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun load(): List<Equipment> {
        if (!file.exists()) {
            return emptyList()
        }

        val content = try {
            file.readText()
        } catch (exception: IOException) {
            throw IllegalStateException(
                "Could not read equipment data from ${file.absolutePath}.",
                exception
            )
        }

        return decodeEquipment(content, file.absolutePath)
    }

    fun loadInitialData(): List<Equipment> {
        val resource = javaClass.getResourceAsStream("/equipment-sample.json")
            ?: throw IllegalStateException("Could not find equipment-sample.json resource.")

        val content = resource.bufferedReader().use { reader -> reader.readText() }
        return decodeEquipment(content, "equipment-sample.json resource")
    }

    fun save(equipment: List<Equipment>) {
        file.parentFile?.let { parent ->
            if (!parent.exists() && !parent.mkdirs()) {
                throw IOException("Could not create data directory: ${parent.absolutePath}")
            }
        }

        val records = equipment.map(EquipmentJsonRecord::fromDomain)
        val temporaryFile = File.createTempFile(
            "${file.name}.",
            ".tmp",
            file.absoluteFile.parentFile
        )

        try {
            temporaryFile.writeText(json.encodeToString(records))
            try {
                Files.move(temporaryFile.toPath(), file.toPath(), ATOMIC_MOVE, REPLACE_EXISTING)
            } catch (atomicMoveException: AtomicMoveNotSupportedException) {
                try {
                    Files.move(temporaryFile.toPath(), file.toPath(), REPLACE_EXISTING)
                } catch (fallbackException: IOException) {
                    fallbackException.addSuppressed(atomicMoveException)
                    throw fallbackException
                }
            }
        } finally {
            temporaryFile.delete()
        }
    }

    fun exists(): Boolean = file.exists()

    private fun decodeEquipment(content: String, source: String): List<Equipment> {
        return try {
            json.decodeFromString<List<EquipmentJsonRecord>>(content)
                .map(EquipmentJsonRecord::toDomain)
        } catch (exception: SerializationException) {
            throw IllegalStateException(
                "Could not read equipment data from $source. Check that the JSON file is valid.",
                exception
            )
        }
    }
}
