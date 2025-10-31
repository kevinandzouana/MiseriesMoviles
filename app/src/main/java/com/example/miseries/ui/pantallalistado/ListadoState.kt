package com.example.miseries.ui.pantallalistado

import com.example.miseries.domain.modelo.Serie

data class ListadoState(
    val series: List<Serie> = emptyList()
)

