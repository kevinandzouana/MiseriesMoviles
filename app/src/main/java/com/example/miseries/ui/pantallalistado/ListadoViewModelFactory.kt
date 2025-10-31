package com.example.miseries.ui.pantallalistado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miseries.domain.usecase.GetSeriesUseCase

class ListadoViewModelFactory(
    private val getSeriesUseCase: GetSeriesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListadoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListadoViewModel(getSeriesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

