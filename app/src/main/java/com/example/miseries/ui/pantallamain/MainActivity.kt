package com.example.miseries.ui.pantallamain

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.miseries.R
import com.example.miseries.domain.usecase.AddSeriesUseCase
import com.example.miseries.domain.usecase.BorrarSerieUseCase
import com.example.miseries.domain.usecase.GetSeriesUseCase
import com.example.miseries.ui.pantalladescripcion.DescripcionActivity
import com.example.miseries.ui.pantallalistado.ListadoActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var etNombreSerie: EditText
    private lateinit var etTemporadas: EditText
    private lateinit var etEmpresa: EditText
    private lateinit var btnAnadir: Button
    private lateinit var btnBorrar: Button
    private lateinit var btnListado: Button
    private lateinit var btnDescripcion: Button

    private lateinit var listadoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initActivityResultLauncher()
        initViewModel()
        initViews()
        setupListeners()
        observeViewModel()
    }

    private fun initActivityResultLauncher() {
        listadoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val titulo = data.getStringExtra("SERIE_TITULO") ?: ""
                    val temporadas = data.getIntExtra("SERIE_TEMPORADAS", 1)
                    val empresa = data.getStringExtra("SERIE_EMPRESA") ?: ""

                    // Rellenar los campos con la información de la serie seleccionada
                    etNombreSerie.setText(titulo)
                    etTemporadas.setText(temporadas.toString())
                    etEmpresa.setText(empresa)
                }
            }
        }
    }

    private fun initViewModel() {
        val getSeriesUseCase = GetSeriesUseCase()
        val addSeriesUseCase = AddSeriesUseCase()
        val borrarSerieUseCase = BorrarSerieUseCase()

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(getSeriesUseCase, addSeriesUseCase, borrarSerieUseCase)
        )[MainViewModel::class.java]
    }

    private fun initViews() {
        etNombreSerie = findViewById(R.id.etNombreSerie)
        etTemporadas = findViewById(R.id.etTemporadas)
        etEmpresa = findViewById(R.id.etEmpresa)
        btnAnadir = findViewById(R.id.añadir)
        btnBorrar = findViewById(R.id.borrar)
        btnListado = findViewById(R.id.listado)
        btnDescripcion = findViewById(R.id.descripcionbtn)
    }

    private fun setupListeners() {
        btnAnadir.setOnClickListener {
            val nombre = etNombreSerie.text.toString().trim()
            val temporadasStr = etTemporadas.text.toString().trim()
            val empresa = etEmpresa.text.toString().trim()

            if (nombre.isBlank()) {
                Toast.makeText(this, "Por favor ingresa el nombre de la serie", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val temporadas = temporadasStr.toIntOrNull() ?: 1

            val exito = viewModel.addSerie(nombre, temporadas, empresa)
            if (exito) {
                Toast.makeText(this, "Serie añadida correctamente", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }

        btnBorrar.setOnClickListener {
            val nombre = etNombreSerie.text.toString().trim()
            if (nombre.isBlank()) {
                Toast.makeText(this, "Por favor ingresa el nombre de la serie a borrar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val serie = viewModel.uiState.value?.series?.find {
                it.titulo.equals(nombre, ignoreCase = true)
            }

            if (serie != null) {
                viewModel.borrarSerie(serie)
                clearFields()
            } else {
                Toast.makeText(this, "Serie no encontrada", Toast.LENGTH_SHORT).show()
            }
        }

        btnListado.setOnClickListener {
            val intent = Intent(this, ListadoActivity::class.java)
            listadoLauncher.launch(intent)
        }

        btnDescripcion.setOnClickListener {
            val nombre = etNombreSerie.text.toString().trim()
            val temporadasStr = etTemporadas.text.toString().trim()

            if (nombre.isBlank()) {
                Toast.makeText(this, "Por favor ingresa el nombre de la serie", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val temporadas = temporadasStr.toIntOrNull() ?: 1

            val intent = Intent(this, DescripcionActivity::class.java).apply {
                putExtra("NOMBRE_SERIE", nombre)
                putExtra("TEMPORADAS", temporadas)
            }
            startActivity(intent)
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

    private fun clearFields() {
        etNombreSerie.text.clear()
        etTemporadas.text.clear()
        etEmpresa.text.clear()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadSeries()
    }
}