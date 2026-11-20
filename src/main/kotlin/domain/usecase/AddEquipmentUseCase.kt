package org.example.domain.usecase

import org.example.domain.model.Equipment
import org.example.domain.repository.EquipmentRepository

class AddEquipmentUseCase(
    private val repository: EquipmentRepository
) {
    operator fun invoke(equipment: Equipment) {
        require(repository.findById(equipment.id) == null) {
            "Equipment with ID ${equipment.id} already exists."
        }

        repository.add(equipment)
    }
}