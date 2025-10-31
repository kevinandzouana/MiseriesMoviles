package com.example.miseries.ui.pantallamain

import com.example.miseries.domain.modelo.Serie

data class MainState(
    val series: List<Serie> = emptyList(),
    val serieActual: Serie? = null,
    val mensaje: String? = null
)