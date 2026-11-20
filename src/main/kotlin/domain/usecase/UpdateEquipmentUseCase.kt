package org.example.domain.usecase


import org.example.domain.model.Equipment
import org.example.domain.repository.EquipmentRepository

class UpdateEquipmentUseCase(
    private val repository: EquipmentRepository
) {
    operator fun invoke(equipment: Equipment): Boolean {
        return repository.update(equipment)
    }
}