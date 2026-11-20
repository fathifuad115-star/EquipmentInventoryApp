package org.example.domain.usecase

import org.example.domain.model.Equipment
import org.example.domain.repository.EquipmentRepository

class SearchEquipmentByIdUseCase(
    private val repository: EquipmentRepository
) {
    operator fun invoke(id: String): Equipment? {
        require(id.isNotBlank())
        return repository.findById(id)
    }
}