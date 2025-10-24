package com.example.miseries.ui.pantallamain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.example.miseries.domain.usecase.GetSeriesUseCase

class MainViewModel (

private val getSeries: GetSeriesUseCase,
) : ViewModel(){
private val _uiState= MutableLiveData(MainState())

val uiState: MutableLiveData<MainState> get()=_uiState

    init {
getSeries()
    }
    fun getSeries(){
        _uiState.value=_uiState.value?.copy(series= getSeries.invoke())
    }



}
