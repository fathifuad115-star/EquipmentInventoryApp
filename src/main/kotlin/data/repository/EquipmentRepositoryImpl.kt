package org.example.data.repository

import org.example.data.datasource.EquipmentJsonDataSource
import org.example.domain.model.Equipment
import org.example.domain.repository.EquipmentRepository

class EquipmentRepositoryImpl(
    private val dataSource: EquipmentJsonDataSource
) : EquipmentRepository {

    private val items = dataSource.load().toMutableList()

    init {
        if (items.isEmpty()) {
            items.addAll(sampleEquipment())
            saveItems()
        }
    }

    override fun add(equipment: Equipment) {
        require(items.none { it.id == equipment.id }) {
            "Equipment with ID ${equipment.id} already exists."
        }

        items.add(equipment)
        saveItems()
    }

    override fun findById(id: String): Equipment? {
        return items.find { it.id == id }
    }

    override fun update(equipment: Equipment): Boolean {
        val index = items.indexOfFirst { it.id == equipment.id }

        if (index == -1) {
            return false
        }

        items[index] = equipment
        saveItems()
        return true
    }

    override fun deleteById(id: String): Boolean {
        val removed = items.removeIf { it.id == id }

        if (removed) {
            saveItems()
        }

        return removed
    }

    override fun getAll(): List<Equipment> {
        return items.toList()
    }

    private fun saveItems() {
        dataSource.save(items)
    }

    private fun sampleEquipment(): List<Equipment> {
        return listOf(
            Equipment("EQ-001", "Desktop Computer", "Lab A", "Good"),
            Equipment("EQ-002", "Monitor", "Lab A", "Good"),
            Equipment("EQ-003", "Keyboard", "Lab A", "Fair"),
            Equipment("EQ-004", "Mouse", "Lab A", "Good"),
            Equipment("EQ-005", "Printer", "Lab B", "Needs Repair"),
            Equipment("EQ-006", "Projector", "Lab B", "Good"),
            Equipment("EQ-007", "Router", "Server Room", "Good"),
            Equipment("EQ-008", "Network Switch", "Server Room", "Fair"),
            Equipment("EQ-009", "Laptop", "Lab C", "Good"),
            Equipment("EQ-010", "UPS", "Server Room", "Needs Repair")
        )
    }
}