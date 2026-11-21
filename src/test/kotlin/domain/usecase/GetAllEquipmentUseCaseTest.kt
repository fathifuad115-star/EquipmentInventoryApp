package org.example.domain.usecase

import org.example.domain.model.Equipment
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllEquipmentUseCaseTest {
    @Test
    fun `should return all equipment`() {
        val items = listOf(
            Equipment("EQ-001", "Desktop", "Lab A", "Good"),
            Equipment("EQ-002", "Monitor", "Lab A", "Fair")
        )
        val repository = FakeEquipmentRepository().apply { items.forEach(::add) }

        assertEquals(items, GetAllEquipmentUseCase(repository)())
    }

    @Test
    fun `should return an empty list when no equipment exists`() {
        assertEquals(emptyList(), GetAllEquipmentUseCase(FakeEquipmentRepository())())
    }
}
