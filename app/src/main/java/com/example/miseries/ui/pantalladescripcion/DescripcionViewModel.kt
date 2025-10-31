package com.example.miseries.ui.pantalladescripcion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miseries.domain.usecase.UpdateSerieUseCase

class DescripcionViewModel(
    private val updateSerieUseCase: UpdateSerieUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(DescripcionState())
    val uiState: LiveData<DescripcionState> get() = _uiState

    fun setDatosSerie(nombre: String, temporadas: Int) {
        _uiState.value = _uiState.value?.copy(
            nombreSerie = nombre,
            temporadas = temporadas
        )
    }

    fun setDescripcion(descripcion: String) {
        _uiState.value = _uiState.value?.copy(descripcion = descripcion)
    }

    fun guardarDescripcion(descripcionAntigua: String, descripcionNueva: String): Boolean {
        if (descripcionNueva.isBlank()) {
            _uiState.value = _uiState.value?.copy(mensaje = "La descripción no puede estar vacía")
            return false
        }

        val exito = updateSerieUseCase.invoke(descripcionAntigua, descripcionNueva)
        if (exito) {
            _uiState.value = _uiState.value?.copy(mensaje = "Descripción guardada correctamente")
        } else {
            _uiState.value = _uiState.value?.copy(mensaje = "Error al guardar la descripción")
        }
        return exito
    }

    fun clearMensaje() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }
}

