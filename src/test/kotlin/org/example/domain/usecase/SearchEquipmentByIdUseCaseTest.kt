package org.example.domain.usecase

import org.example.domain.model.Equipment
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class SearchEquipmentByIdUseCaseTest {
    private val equipment = Equipment("EQ-001", "Desktop", "Lab A", "Good")

    @Test
    fun `should return equipment when ID exists`() {
        val repository = FakeEquipmentRepository().apply { add(equipment) }

        assertEquals(equipment, SearchEquipmentByIdUseCase(repository)("EQ-001"))
    }

    @Test
    fun `should return null when ID does not exist`() {
        assertNull(SearchEquipmentByIdUseCase(FakeEquipmentRepository())("EQ-404"))
    }

    @Test
    fun `should reject a blank ID`() {
        assertFailsWith<IllegalArgumentException> {
            SearchEquipmentByIdUseCase(FakeEquipmentRepository())(" ")
        }
    }
}
