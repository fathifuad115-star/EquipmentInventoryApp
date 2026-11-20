package org.example.domain.usecase

import org.example.domain.model.Equipment
import org.example.domain.repository.EquipmentRepository

class GetAllEquipmentUseCase(
    private val repository: EquipmentRepository
) {
    operator fun invoke(): List<Equipment> {
        return repository.getAll()
    }
}