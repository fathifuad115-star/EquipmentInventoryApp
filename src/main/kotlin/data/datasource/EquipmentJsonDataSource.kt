package org.example.data.datasource

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.domain.model.Equipment
import java.io.File

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

        return json.decodeFromString(file.readText())
    }

    fun save(equipment: List<Equipment>) {
        file.parentFile?.mkdirs()
        file.writeText(json.encodeToString(equipment))
    }
}