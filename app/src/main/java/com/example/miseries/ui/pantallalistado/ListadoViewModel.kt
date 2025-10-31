package com.example.miseries.ui.pantallalistado

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.miseries.domain.usecase.GetSeriesUseCase

class ListadoViewModel(
    private val getSeriesUseCase: GetSeriesUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(ListadoState())
    val uiState: LiveData<ListadoState> get() = _uiState

    init {
        loadSeries()
    }

    fun loadSeries() {
        val series = getSeriesUseCase.invoke()
        _uiState.value = _uiState.value?.copy(series = series)
    }
}

