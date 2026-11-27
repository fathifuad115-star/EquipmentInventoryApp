package org.example.domain.repository

import org.example.domain.model.Equipment

interface EquipmentRepository {
    fun add(equipment: Equipment)
    fun findById(id: String): Equipment?
    fun update(equipment: Equipment): Boolean
    fun deleteById(id: String): Boolean
    fun getAll(): List<Equipment>
}