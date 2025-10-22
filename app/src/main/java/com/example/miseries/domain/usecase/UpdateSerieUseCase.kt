package com.example.miseries.domain.usecase

import com.example.miseries.Data.RepositorioSeries

class UpdateSerieUseCase {
    operator fun invoke (descripcionAntigua: String, descripcionNueva: String): Boolean{
        return RepositorioSeries.updateSerie(descripcionAntigua,descripcionNueva)
    }
}