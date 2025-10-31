package com.example.miseries.ui.pantallamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miseries.domain.modelo.Serie
import com.example.miseries.domain.usecase.AddSeriesUseCase
import com.example.miseries.domain.usecase.BorrarSerieUseCase
import com.example.miseries.domain.usecase.GetSeriesUseCase

class MainViewModel(
    private val getSeriesUseCase: GetSeriesUseCase,
    private val addSeriesUseCase: AddSeriesUseCase,
    private val borrarSerieUseCase: BorrarSerieUseCase
) : ViewModel() {
    
    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        loadSeries()
    }

    fun loadSeries() {
        val series = getSeriesUseCase.invoke()
        _uiState.value = _uiState.value?.copy(series = series)
    }

    fun addSerie(titulo: String, temporadas: Int, empresa: String): Boolean {
        if (titulo.isBlank()) {
            _uiState.value = _uiState.value?.copy(mensaje = "El nombre de la serie no puede estar vacío")
            return false
        }
        
        val nuevaSerie = Serie(
            titulo = titulo.trim(),
            temporadas = temporadas,
            empresa = empresa.trim(),
            descripcion = ""
        )
        
        val exito = addSeriesUseCase.invoke(nuevaSerie)
        if (exito) {
            _uiState.value = _uiState.value?.copy(
                serieActual = nuevaSerie,
                mensaje = "Serie añadida correctamente"
            )
            loadSeries()
        } else {
            _uiState.value = _uiState.value?.copy(mensaje = "La serie ya existe")
        }
        return exito
    }

    fun borrarSerie(serie: Serie): Boolean {
        val exito = borrarSerieUseCase.invoke(serie)
        if (exito) {
            _uiState.value = _uiState.value?.copy(mensaje = "Serie borrada correctamente")
            loadSeries()
        } else {
            _uiState.value = _uiState.value?.copy(mensaje = "Error al borrar la serie")
        }
        return exito
    }

    fun clearMensaje() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }
}
