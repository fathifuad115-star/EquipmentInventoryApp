package org.example.domain.usecase

import org.example.domain.model.Equipment
import org.example.domain.repository.EquipmentRepository

class FakeEquipmentRepository : EquipmentRepository {
    private val items = mutableListOf<Equipment>()

    override fun add(equipment: Equipment) {
        require(items.none { it.id == equipment.id }) {
            "Equipment with ID ${equipment.id} already exists."
        }
        items.add(equipment)
    }

    override fun findById(id: String): Equipment? = items.find { it.id == id }

    override fun update(equipment: Equipment): Boolean {
        val index = items.indexOfFirst { it.id == equipment.id }
        if (index == -1) return false
        items[index] = equipment
        return true
    }

    override fun deleteById(id: String): Boolean = items.removeIf { it.id == id }

    override fun getAll(): List<Equipment> = items.toList()
}
