package org.example.data.repository

import org.example.data.datasource.EquipmentJsonDataSource
import org.example.domain.model.Equipment
import org.example.domain.repository.EquipmentRepository

class EquipmentRepositoryImpl(
    private val dataSource: EquipmentJsonDataSource
) : EquipmentRepository {

    private var items = dataSource.load()

    init {
        if (!dataSource.exists()) {
            val samples = dataSource.loadInitialData()
            dataSource.save(samples)
            items = samples
        }
    }

    override fun add(equipment: Equipment) {
        require(items.none { it.id == equipment.id }) {
            "Equipment with ID ${equipment.id} already exists."
        }

        val updatedItems = items + equipment
        dataSource.save(updatedItems)
        items = updatedItems
    }

    override fun findById(id: String): Equipment? {
        return items.find { it.id == id }
    }

    override fun update(equipment: Equipment): Boolean {
        val index = items.indexOfFirst { it.id == equipment.id }

        if (index == -1) {
            return false
        }

        val updatedItems = items.toMutableList().apply { this[index] = equipment }
        dataSource.save(updatedItems)
        items = updatedItems
        return true
    }

    override fun deleteById(id: String): Boolean {
        val updatedItems = items.filterNot { it.id == id }

        if (updatedItems.size == items.size) {
            return false
        }

        dataSource.save(updatedItems)
        items = updatedItems
        return true
    }

    override fun getAll(): List<Equipment> {
        return items.toList()
    }
}
