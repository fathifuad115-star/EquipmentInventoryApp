package org.example.domain.usecase

import org.example.domain.model.Equipment
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DeleteEquipmentUseCaseTest {
    @Test
    fun `should delete equipment when ID exists`() {
        val repository = FakeEquipmentRepository().apply {
            add(Equipment("EQ-001", "Desktop", "Lab A", "Good"))
        }

        assertTrue(DeleteEquipmentUseCase(repository)("EQ-001"))
        assertNull(repository.findById("EQ-001"))
    }

    @Test
    fun `should return false when deleting an unknown ID`() {
        assertFalse(DeleteEquipmentUseCase(FakeEquipmentRepository())("EQ-404"))
    }

    @Test
    fun `should reject a blank ID`() {
        assertFailsWith<IllegalArgumentException> {
            DeleteEquipmentUseCase(FakeEquipmentRepository())(" ")
        }
    }
}
