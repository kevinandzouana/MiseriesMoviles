package com.example.miseries.domain.usecase

import com.example.miseries.Data.RepositorioSeries
import com.example.miseries.domain.modelo.Serie

class AddSeriesUseCase {
    operator  fun invoke(serie: Serie): Boolean{
        return RepositorioSeries.addSerie(serie)
    }
}