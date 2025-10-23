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

    fun addSerie(serie: Serie): Boolean {
        // Verificar si ya existe una serie con el mismo título
        val yaExiste = series.any { it.titulo.equals(serie.titulo, ignoreCase = true) }
        return if (!yaExiste) {
            series.add(serie)
        } else {
            false // No se agrega si ya existe
        }
    }

    fun getSeries(): List<Serie> {
        // Devolver lista sin duplicados basándose en el título
        return series.distinctBy { it.titulo.lowercase() }
    }

    fun borrarSerie(serie: Serie): Boolean {
       return series.removeIf { it.titulo== serie.titulo }

    }
    fun updateSerie(descripcionAntigua: String, descripcionNueva: String): Boolean {
        val serieIndex = series.indexOfFirst { it.descripcion == descripcionAntigua }
        return if (serieIndex != -1) { // <- debe ser -1, no 1
            val serieActual = series[serieIndex]
            val serieActualizada = serieActual.copy(descripcion = descripcionNueva)
            series[serieIndex] = serieActualizada
            true
        } else {
            false
        }
    }
}