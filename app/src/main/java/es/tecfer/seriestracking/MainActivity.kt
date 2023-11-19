package es.tecfer.seriestracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seriesList = ArrayList<Serie>()

        val recyclerView = findViewById<RecyclerView>(R.id.seriesRecyclerView)
        val adapter = SeriesAdapter(seriesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val titleEditText = findViewById<EditText>(R.id.seriesTitleEditText)
            val title = titleEditText.text.toString()
            // Agregar la nueva serie/película a la lista
            seriesList.add(Serie(title, 0, 0, false))
            adapter.notifyDataSetChanged() // Actualizar el RecyclerView
            titleEditText.text.clear() // Limpiar el campo de entrada
        }

        // Para guardar
        val sharedPreferences = getSharedPreferences("SeriesPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("seriesList", Gson().toJson(seriesList))
        editor.apply()

        // Para cargar
        val savedSeries = sharedPreferences.getString("seriesList", null)
        if (savedSeries != null) {
            seriesList.addAll(Gson().fromJson(savedSeries, object : TypeToken<List<Serie>>() {}.type))
            adapter.notifyDataSetChanged()
        }



    }
}