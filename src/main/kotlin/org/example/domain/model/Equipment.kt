package org.example.domain.model

data class Equipment(
    val id: String,
    val name: String,
    val location: String,
    val condition: String
) {
    init {

        require(id.isNotBlank()) { "Equipment ID cannot be blank." }
        require(name.isNotBlank()) { "Equipment name cannot be blank." }
        require(location.isNotBlank()) { "Equipment location cannot be blank." }
        require(condition.isNotBlank()) { "Equipment condition cannot be blank." }
    }
}
