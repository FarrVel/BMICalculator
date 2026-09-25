package com.example.bmicalculator2

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etBeratBadan = findViewById<EditText>(R.id.etBeratBadan)
        val etTinggiBadan = findViewById<EditText>(R.id.etTinggiBadan)
        val btnHitung = findViewById<MaterialButton>(R.id.btnHitung)
        val btnReset = findViewById<MaterialButton>(R.id.btnReset)
        val btnKategori = findViewById<MaterialButton>(R.id.btnKategori)
        val cardHasil = findViewById<MaterialCardView>(R.id.cardHasil)
        val tvHasilAngka = findViewById<TextView>(R.id.tvHasilAngka)
        val tvKategoriStatus = findViewById<TextView>(R.id.tvKategoriStatus)

        btnHitung.setOnClickListener {
            val beratStr = etBeratBadan.text.toString()
            val tinggiStr = etTinggiBadan.text.toString()

            if (beratStr.isEmpty() || tinggiStr.isEmpty()) {
                Toast.makeText(this, "Mohon isi berat dan tinggi badan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val berat = beratStr.toFloat()
            val tinggiCm = tinggiStr.toFloat()
            val tinggiM = tinggiCm / 100

            val bmi = berat / (tinggiM * tinggiM)

            val bmiFormatted = String.format(Locale.US, "%.1f", bmi)
            tvHasilAngka.text = bmiFormatted

            val (kategori, warnaBackground) = getKategoriBMI(bmi)
            tvKategoriStatus.text = kategori
            tvKategoriStatus.backgroundTintList = ColorStateList.valueOf(warnaBackground)

            cardHasil.visibility = View.VISIBLE
        }

        btnReset.setOnClickListener {
            etBeratBadan.text.clear()
            etTinggiBadan.text.clear()
            cardHasil.visibility = View.GONE
            etBeratBadan.requestFocus()
        }

        btnKategori.setOnClickListener {
            Toast.makeText(this, "Kurang: < 18.5\nNormal: 18.5 - 24.9\nBerlebih: 25 - 29.9\nObesitas: >= 30", Toast.LENGTH_LONG).show()
        }
    }

    private fun getKategoriBMI(bmi: Float): Pair<String, Int> {
        return when {
            bmi < 18.5 -> Pair("Kurus", "#FF9800".toColorInt())
            bmi in 18.5..24.9 -> Pair("Normal", "#34A853".toColorInt())
            bmi in 25.0..29.9 -> Pair("Berlebih", "#F44336".toColorInt())
            else -> Pair("Obesitas", "#B71C1C".toColorInt())
        }
    }
}