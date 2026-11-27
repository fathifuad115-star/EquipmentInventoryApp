package org.example.domain.usecase

import org.example.domain.repository.EquipmentRepository

class DeleteEquipmentUseCase(
    private val repository: EquipmentRepository
) {
    operator fun invoke(id: String): Boolean {
        require(id.isNotBlank())
        return repository.deleteById(id)
    }
}