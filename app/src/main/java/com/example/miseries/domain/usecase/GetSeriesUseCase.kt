package com.example.miseries.domain.usecase

import com.example.miseries.Data.RepositorioSeries
import com.example.miseries.domain.modelo.Serie

class GetSeriesUseCase {
    operator fun invoke(): List<Serie> = RepositorioSeries.getSeries()
}