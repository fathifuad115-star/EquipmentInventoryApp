package org.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Equipment(
    val id: String,
    val name: String,
    val location: String,
    val condition: String
) {
    init {

        require(id.isNotBlank())
        require(name.isNotBlank())
        require(location.isNotBlank())
        require(condition.isNotBlank())
    }
}