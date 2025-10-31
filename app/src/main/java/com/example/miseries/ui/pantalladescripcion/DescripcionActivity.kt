package com.example.miseries.ui.pantalladescripcion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.miseries.R
import com.example.miseries.domain.usecase.UpdateSerieUseCase


class DescripcionActivity : AppCompatActivity() {

    private lateinit var viewModel: DescripcionViewModel
    private lateinit var etNombreSerie: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.descripcion_pantalla)

        initViewModel()
        initViews()
        loadDataFromIntent()
        setupListeners()
        observeViewModel()
    }

    private fun initViewModel() {
        val updateSerieUseCase = UpdateSerieUseCase()
        viewModel = ViewModelProvider(
            this,
            DescripcionViewModelFactory(updateSerieUseCase)
        )[DescripcionViewModel::class.java]
    }

    private fun initViews() {
        etNombreSerie = findViewById(R.id.nombreSerie)
        etDescripcion = findViewById(R.id.descripcioncaja)
        btnGuardar = findViewById(R.id.guardarDescripcion)
        btnVolver = findViewById(R.id.volver)
    }

    private fun loadDataFromIntent() {
        val nombreSerie = intent.getStringExtra("NOMBRE_SERIE") ?: ""
        val temporadas = intent.getIntExtra("TEMPORADAS", 1)

        viewModel.setDatosSerie(nombreSerie, temporadas)
        etNombreSerie.setText(nombreSerie)
        etNombreSerie.isEnabled = false // No permitir editar el nombre
    }

    private fun setupListeners() {
        btnGuardar.setOnClickListener {
            val descripcion = etDescripcion.text.toString().trim()

            if (descripcion.isBlank()) {
                Toast.makeText(this, "Por favor ingresa una descripción", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val exito = viewModel.guardarDescripcion("", descripcion)
            if (exito) {
                Toast.makeText(this, "Descripción guardada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            state.mensaje?.let { mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                viewModel.clearMensaje()
            }
        }
    }
}
