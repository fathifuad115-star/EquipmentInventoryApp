package org.example.data.repository

import org.example.data.datasource.EquipmentJsonDataSource
import org.example.domain.model.Equipment
import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class EquipmentRepositoryImplTest {

    @Test
    fun `should seed sample data only when the JSON file is missing`() {
        withTemporaryDirectory { directory ->
            val file = File(directory, "equipment.json")
            val repository = EquipmentRepositoryImpl(EquipmentJsonDataSource(file))

            assertEquals(10, repository.getAll().size)
            assertEquals(10, EquipmentRepositoryImpl(EquipmentJsonDataSource(file)).getAll().size)
        }
    }

    @Test
    fun `should persist equipment after the repository is recreated`() {
        withTemporaryDirectory { directory ->
            val file = File(directory, "equipment.json")
            val repository = EquipmentRepositoryImpl(EquipmentJsonDataSource(file))
            val equipment = Equipment("EQ-011", "Webcam", "Lab C", "Good")

            repository.add(equipment)

            val reloaded = EquipmentRepositoryImpl(EquipmentJsonDataSource(file))
            assertEquals(equipment, reloaded.findById("EQ-011"))
        }
    }

    @Test
    fun `should keep the list empty after all equipment is deleted`() {
        withTemporaryDirectory { directory ->
            val file = File(directory, "equipment.json")
            val repository = EquipmentRepositoryImpl(EquipmentJsonDataSource(file))

            repository.getAll().forEach { repository.deleteById(it.id) }

            val reloaded = EquipmentRepositoryImpl(EquipmentJsonDataSource(file))
            assertEquals(emptyList(), reloaded.getAll())
        }
    }

    @Test
    fun `should report an error when the JSON file is invalid`() {
        withTemporaryDirectory { directory ->
            val file = File(directory, "equipment.json").apply { writeText("{invalid") }

            val exception = assertFailsWith<IllegalStateException> {
                EquipmentJsonDataSource(file).load()
            }

            assertNotNull(exception.message)
        }
    }

    private fun withTemporaryDirectory(action: (File) -> Unit) {
        val directory = Files.createTempDirectory("equipment-inventory-test").toFile()
        try {
            action(directory)
        } finally {
            directory.deleteRecursively()
        }
    }
}
