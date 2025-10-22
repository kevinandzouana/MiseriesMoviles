package com.example.miseries.Data

import com.example.miseries.domain.modelo.Serie

object RepositorioSeries {

    private val series = mutableListOf<Serie>()

    init {
        series.add(Serie("Stranger Things",5,"Netflix","Serie de terror juvenil"))
    }
    fun size() = series.size

    val numSerie: Int
        get()= series.size

    fun getSerie(id:Int) = series[id]

    fun addSerie(serie: Serie)= series.add(serie)

    fun getSeries(): List<Serie> = series.toList()

    fun borrarSerie(serie: Serie) {
        series.removeIf { it.titulo== serie.titulo }

    }
}