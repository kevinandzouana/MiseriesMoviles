package com.example.miseries.domain.usecase

import com.example.miseries.Data.RepositorioSeries
import com.example.miseries.domain.modelo.Serie

class VerSerieUseCase {
    operator fun invoke(id:Int): Serie= RepositorioSeries.getSerie(id)
}