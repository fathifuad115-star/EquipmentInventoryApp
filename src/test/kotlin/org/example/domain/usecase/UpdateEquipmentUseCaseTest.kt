package org.example.domain.usecase

import org.example.domain.model.Equipment
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UpdateEquipmentUseCaseTest {
    @Test
    fun `should update equipment when ID exists`() {
        val original = Equipment("EQ-001", "Desktop", "Lab A", "Good")
        val updated = original.copy(condition = "Needs Repair")
        val repository = FakeEquipmentRepository().apply { add(original) }

        assertTrue(UpdateEquipmentUseCase(repository)(updated))
        assertEquals(updated, repository.findById("EQ-001"))
    }

    @Test
    fun `should return false when updating an unknown ID`() {
        val equipment = Equipment("EQ-404", "Desktop", "Lab A", "Good")

        assertFalse(UpdateEquipmentUseCase(FakeEquipmentRepository())(equipment))
    }
}
