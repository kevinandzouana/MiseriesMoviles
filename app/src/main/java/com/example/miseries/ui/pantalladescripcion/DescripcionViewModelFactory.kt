package com.example.miseries.ui.pantalladescripcion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miseries.domain.usecase.UpdateSerieUseCase

class DescripcionViewModelFactory(
    private val updateSerieUseCase: UpdateSerieUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DescripcionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DescripcionViewModel(updateSerieUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

