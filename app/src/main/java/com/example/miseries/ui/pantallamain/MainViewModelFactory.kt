package com.example.miseries.ui.pantallamain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miseries.domain.usecase.AddSeriesUseCase
import com.example.miseries.domain.usecase.BorrarSerieUseCase
import com.example.miseries.domain.usecase.GetSeriesUseCase

class MainViewModelFactory(
    private val getSeriesUseCase: GetSeriesUseCase,
    private val addSeriesUseCase: AddSeriesUseCase,
    private val borrarSerieUseCase: BorrarSerieUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getSeriesUseCase, addSeriesUseCase, borrarSerieUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

