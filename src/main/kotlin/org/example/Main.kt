package org.example

import org.example.data.datasource.EquipmentJsonDataSource
import org.example.data.repository.EquipmentRepositoryImpl
import org.example.domain.usecase.AddEquipmentUseCase
import org.example.domain.usecase.DeleteEquipmentUseCase
import org.example.domain.usecase.GetAllEquipmentUseCase
import org.example.domain.usecase.SearchEquipmentByIdUseCase
import org.example.domain.usecase.UpdateEquipmentUseCase
import org.example.presentation.cli.EquipmentConsoleApp

fun main() {
    val dataSource = EquipmentJsonDataSource()
    val repository = EquipmentRepositoryImpl(dataSource)

    val app = EquipmentConsoleApp(
        addEquipment = AddEquipmentUseCase(repository),
        searchEquipment = SearchEquipmentByIdUseCase(repository),
        getAllEquipment = GetAllEquipmentUseCase(repository),
        updateEquipment = UpdateEquipmentUseCase(repository),
        deleteEquipment = DeleteEquipmentUseCase(repository)
    )

    app.run()
}