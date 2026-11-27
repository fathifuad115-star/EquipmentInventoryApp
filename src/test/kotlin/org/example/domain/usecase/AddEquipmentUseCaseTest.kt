package org.example.domain.usecase

import org.example.domain.model.Equipment
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AddEquipmentUseCaseTest {

    private val equipment = Equipment(
        id = "EQ-001",
        name = "Desktop Computer",
        location = "Lab A",
        condition = "Good"
    )

    @Test
    fun `should add equipment when ID is new`() {
        val repository = FakeEquipmentRepository()
        val useCase = AddEquipmentUseCase(repository)

        useCase(equipment)

        assertEquals(equipment, repository.findById("EQ-001"))
    }

    @Test
    fun `should reject equipment when ID already exists`() {
        val repository = FakeEquipmentRepository()
        repository.add(equipment)
        val useCase = AddEquipmentUseCase(repository)

        assertFailsWith<IllegalArgumentException> {
            useCase(equipment)
        }
    }
}
