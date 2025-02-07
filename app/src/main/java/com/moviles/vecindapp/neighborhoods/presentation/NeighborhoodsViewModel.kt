package com.moviles.vecindapp.neighborhoods.presentation


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.vecindapp.core.token.TokenManager
import com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodRequest
import com.moviles.vecindapp.neighborhoods.data.repository.NeighborhoodRepositoryImpl
import com.moviles.vecindapp.neighborhoods.domain.usecase.AddNeighborhoodUseCase
import com.moviles.vecindapp.neighborhoods.domain.usecase.GetNeighborhoodsUseCase
import kotlinx.coroutines.launch

class NeighborhoodsViewModel : ViewModel() {
    var nombre = mutableStateOf("")
    var direccion = mutableStateOf("")
    var colonia = mutableStateOf("")
    var estado = mutableStateOf("")
    var numeroCasas = mutableStateOf("")

    var neighborhoodsList = mutableStateOf<List<com.moviles.vecindapp.neighborhoods.data.model.NeighborhoodResponse>>(emptyList())
    var errorMessage = mutableStateOf("")


    private val repository = NeighborhoodRepositoryImpl()
    private val addNeighborhoodUseCase = AddNeighborhoodUseCase(repository)
    private val getNeighborhoodsUseCase = GetNeighborhoodsUseCase(repository)

    val showError = mutableStateOf(false)
    val errorFields = mutableStateOf<List<String>>(emptyList())

    fun validateInputs(): Boolean {
        val errors = mutableListOf<String>()

        if (nombre.value.isBlank()) errors.add("nombre")
        if (direccion.value.isBlank()) errors.add("direccion")
        if (colonia.value.isBlank()) errors.add("colonia")
        if (estado.value.isBlank()) errors.add("estado")
        if (numeroCasas.value.isBlank()) errors.add("numeroCasas")

        errorFields.value = errors
        showError.value = errors.isNotEmpty()

        return errors.isEmpty()
    }

    fun onAddNeighborhood() {
        viewModelScope.launch {
            try {
                if (!validateInputs()) {
                    showError.value = true
                    return@launch
                }
                val numCasas = numeroCasas.value.toIntOrNull() ?: 0
                val request = NeighborhoodRequest(
                    nombre = nombre.value,
                    direccion = direccion.value,
                    colonia = colonia.value,
                    estado = estado.value,
                    numeroCasas = numCasas
                )
                addNeighborhoodUseCase(request)

                nombre.value = ""
                direccion.value = ""
                colonia.value = ""
                estado.value = ""
                numeroCasas.value = ""
                showError.value = false
                getNeighborhoods()
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Error al agregar vecindario"
            }
        }
    }

    fun getNeighborhoods() {
        viewModelScope.launch {
            try {
                val list = getNeighborhoodsUseCase()
                neighborhoodsList.value = list
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Error al obtener vecindarios"
            }
        }
    }

    fun logout() {
        TokenManager.token = ""
    }
}
