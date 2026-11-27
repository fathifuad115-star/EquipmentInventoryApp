package org.example.data.datasource

import kotlinx.serialization.Serializable
import org.example.domain.model.Equipment

@Serializable
internal data class EquipmentJsonRecord(
    val id: String,
    val name: String,
    val location: String,
    val condition: String
) {
    fun toDomain(): Equipment = Equipment(id, name, location, condition)

    companion object {
        fun fromDomain(equipment: Equipment): EquipmentJsonRecord = EquipmentJsonRecord(
            id = equipment.id,
            name = equipment.name,
            location = equipment.location,
            condition = equipment.condition
        )
    }
}
