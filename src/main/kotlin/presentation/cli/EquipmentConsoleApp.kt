package org.example.presentation.cli

import org.example.domain.model.Equipment
import org.example.domain.usecase.AddEquipmentUseCase
import org.example.domain.usecase.DeleteEquipmentUseCase
import org.example.domain.usecase.GetAllEquipmentUseCase
import org.example.domain.usecase.SearchEquipmentByIdUseCase
import org.example.domain.usecase.UpdateEquipmentUseCase

class EquipmentConsoleApp(
    private val addEquipment: AddEquipmentUseCase,
    private val searchEquipment: SearchEquipmentByIdUseCase,
    private val getAllEquipment: GetAllEquipmentUseCase,
    private val updateEquipment: UpdateEquipmentUseCase,
    private val deleteEquipment: DeleteEquipmentUseCase
) {
    fun run() {
        while (true) {
            println()
            println("=== Equipment Inventory ===")
            println("1. Show all equipment")
            println("2. Search equipment by ID")
            println("3. Add equipment")
            println("4. Update equipment")
            println("5. Delete equipment")
            println("0. Exit")
            print("Choose an option: ")

            when (readlnOrNull()?.trim()) {
                "1" -> showAllEquipment()
                "2" -> searchForEquipment()
                "3" -> addNewEquipment()
                "4" -> updateExistingEquipment()
                "5" -> deleteExistingEquipment()
                "0", null -> return
                else -> println("Invalid option. Please try again.")
            }
        }
    }

    private fun showAllEquipment() {
        val equipment = getAllEquipment()

        if (equipment.isEmpty()) {
            println("No equipment found.")
            return
        }

        equipment.forEach(::printEquipment)
    }

    private fun searchForEquipment() {
        val id = readRequired("Enter equipment ID: ")
        val equipment = searchEquipment(id)

        if (equipment == null) {
            println("Equipment not found.")
        } else {
            printEquipment(equipment)
        }
    }

    private fun addNewEquipment() {
        val equipment = readEquipmentDetails()

        try {
            addEquipment(equipment)
            println("Equipment added successfully.")
        } catch (exception: IllegalArgumentException) {
            println(exception.message ?: "Unable to add equipment.")
        }
    }

    private fun updateExistingEquipment() {
        val id = readRequired("Enter equipment ID to update: ")
        val existingEquipment = searchEquipment(id)

        if (existingEquipment == null) {
            println("Equipment not found.")
            return
        }

        val updatedEquipment = Equipment(
            id = existingEquipment.id,
            name = readRequired("Enter new name: "),
            location = readRequired("Enter new location: "),
            condition = readRequired("Enter new condition: ")
        )

        if (updateEquipment(updatedEquipment)) {
            println("Equipment updated successfully.")
        } else {
            println("Equipment could not be updated.")
        }
    }

    private fun deleteExistingEquipment() {
        val id = readRequired("Enter equipment ID to delete: ")
        val equipment = searchEquipment(id)

        if (equipment == null) {
            println("Equipment not found.")
            return
        }

        print("Delete ${equipment.name} (ID: ${equipment.id})? (y/n): ")
        val confirmation = readlnOrNull()?.trim()

        if (confirmation.equals("y", ignoreCase = true)) {
            if (deleteEquipment(id)) {
                println("Equipment deleted successfully.")
            } else {
                println("Equipment could not be deleted.")
            }
        } else {
            println("Deletion cancelled.")
        }
    }

    private fun readEquipmentDetails(): Equipment {
        return Equipment(
            id = readRequired("Enter equipment ID: "),
            name = readRequired("Enter equipment name: "),
            location = readRequired("Enter equipment location: "),
            condition = readRequired("Enter equipment condition: ")
        )
    }

    private fun readRequired(prompt: String): String {
        while (true) {
            print(prompt)
            val value = readlnOrNull()?.trim()

            if (!value.isNullOrBlank()) {
                return value
            }

            println("This field is required.")
        }
    }

    private fun printEquipment(equipment: Equipment) {
        println(
            "ID: ${equipment.id} | Name: ${equipment.name} | " +
                    "Location: ${equipment.location} | Condition: ${equipment.condition}"
        )
    }
}