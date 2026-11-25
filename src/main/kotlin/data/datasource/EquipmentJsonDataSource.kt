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

        return try {
            json.decodeFromString<List<EquipmentJsonRecord>>(file.readText())
                .map(EquipmentJsonRecord::toDomain)
        } catch (exception: Exception) {
            if (exception !is SerializationException && exception !is IOException) {
                throw exception
            }
            throw IllegalStateException(
                "Could not read equipment data from ${file.absolutePath}. Check that the JSON file is valid.",
                exception
            )
        }
    }

    fun loadInitialData(): List<Equipment> {
        val resource = javaClass.getResourceAsStream("/equipment-sample.json")
            ?: throw IllegalStateException("Could not find equipment-sample.json resource.")

        return resource.bufferedReader().use { reader ->
            json.decodeFromString<List<EquipmentJsonRecord>>(reader.readText())
                .map(EquipmentJsonRecord::toDomain)
        }
    }
    fun save(equipment: List<Equipment>) {
        file.parentFile?.let { parent ->
            if (!parent.exists() && !parent.mkdirs()) {
                throw IOException("Could not create data directory: ${parent.absolutePath}")
            }
        }

        val records = equipment.map(EquipmentJsonRecord::fromDomain)
        val temporaryFile = File.createTempFile("${file.name}.", ".tmp", file.absoluteFile.parentFile)

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
}
