package com.example.miseries.ui.pantallalistado

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.miseries.R
import com.example.miseries.domain.modelo.Serie
import com.example.miseries.domain.usecase.GetSeriesUseCase

class ListadoActivity : AppCompatActivity() {

    private lateinit var viewModel: ListadoViewModel
    private lateinit var listViewSeries: ListView
    private lateinit var adapter: SeriesAdapter
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listado_pantalla)

        initViewModel()
        initViews()
        observeViewModel()
    }

    private fun initViewModel() {
        val getSeriesUseCase = GetSeriesUseCase()
        viewModel = ViewModelProvider(
            this,
            ListadoViewModelFactory(getSeriesUseCase)
        )[ListadoViewModel::class.java]
    }

    private fun initViews() {
        listViewSeries = findViewById(R.id.lista_series)
        adapter = SeriesAdapter(emptyList())
        listViewSeries.adapter = adapter

        // Click en un item del ListView
        listViewSeries.setOnItemClickListener { _, _, position, _ ->
            val serieSeleccionada = adapter.getItem(position)
            serieSeleccionada?.let {
                volverConSerieSeleccionada(it)
            }
        }

        btnVolver = findViewById(R.id.btnVolverListado)
        btnVolver.setOnClickListener {
            finish() // Cierra esta actividad y vuelve a la anterior (MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            adapter.updateSeries(state.series)
        }
    }

    private fun volverConSerieSeleccionada(serie: Serie) {
        val intent = intent.apply {
            putExtra("SERIE_TITULO", serie.titulo)
            putExtra("SERIE_TEMPORADAS", serie.temporadas)
            putExtra("SERIE_EMPRESA", serie.empresa)
            putExtra("SERIE_DESCRIPCION", serie.descripcion)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        // Recargar las series cada vez que volvemos a esta pantalla
        viewModel.loadSeries()
    }

    // Adapter simple para mostrar las series
    private inner class SeriesAdapter(
        private var series: List<Serie>
    ) : ArrayAdapter<Serie>(this, android.R.layout.simple_list_item_2, android.R.id.text1, series) {

        fun updateSeries(newSeries: List<Serie>) {
            series = newSeries
            clear()
            addAll(series)
            notifyDataSetChanged()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: layoutInflater.inflate(android.R.layout.simple_list_item_2, parent, false)
            val serie = series[position]

            val text1 = view.findViewById<TextView>(android.R.id.text1)
            val text2 = view.findViewById<TextView>(android.R.id.text2)

            text1.text = serie.titulo
            text2.text = "${serie.empresa} - ${serie.temporadas} temporadas"

            return view
        }
    }
}

