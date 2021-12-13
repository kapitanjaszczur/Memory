package pl.klonowski.memory

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.annotation.RestrictTo
import kotlinx.coroutines.*
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var tvL : TextView
        var flagaTrudnosc = 1000L
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val przyciskL = findViewById<Button>(R.id.buttonL)
        val przyciskP = findViewById<Button>(R.id.buttonP)
        val przyciskLos = findViewById<Button>(R.id.buttonLos)
        tvL = findViewById(R.id.textViewLos)
        val tvW = findViewById<TextView>(R.id.textViewWynik)
        val rbL = findViewById<RadioButton>(R.id.radioButtonLatwy)
        val rbS = findViewById<RadioButton>(R.id.radioButtonSredni)
        val rbT = findViewById<RadioButton>(R.id.radioButtonTrudny)


        var flagaLos : Boolean? = null //0 - Lewy, 1 - Prawy

        var punkty = 0f
        var mnoznik = 0.5f

        rbL.isChecked = true

        przyciskLos.setOnClickListener {
            przyciskL.isEnabled = true
            przyciskL.isClickable = true
            przyciskP.isEnabled = true
            przyciskP.isClickable = true

            if (rbL.isChecked) {
                flagaTrudnosc = 1000L
                mnoznik = 0.5f
            }
            else if (rbS.isChecked){
                flagaTrudnosc = 400L
                mnoznik = 1f
            }
            else if (rbT.isChecked) {
                flagaTrudnosc = 1
                mnoznik = 1.5f
            }

            when (Random.nextInt(1, 3)) {
                1 -> {
                    println("Lewy")
                    flagaLos=false
                    runBlocking {
                        kamien()
                    }
                }
                2 -> {
                    println("Prawy")
                    flagaLos=true
                    runBlocking {
                        ksiazka()
                    }
                }
            }
        }

        przyciskL.setOnClickListener {
            przyciskL.isEnabled = false
            przyciskL.isClickable = false
            przyciskP.isEnabled = false
            przyciskP.isClickable = false

            when (flagaLos) {
                null -> println("przyciskL null")
                false -> {
                    punkty += 1 * mnoznik
                    tvW.text = "Wynik: $punkty"

                    Toast.makeText(applicationContext, "Dobra odpowiedź!", Toast.LENGTH_SHORT).show()
                }
                true -> {
                    punkty = 0f
                    tvW.text = "Wynik: $punkty"

                    Toast.makeText(applicationContext, "Zła odpowiedź!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        przyciskP.setOnClickListener {
            przyciskL.isEnabled = false
            przyciskL.isClickable = false
            przyciskP.isEnabled = false
            przyciskP.isClickable = false

            when (flagaLos) {
                null -> println("przycikP null")
                false -> {
                    punkty = 0f
                    tvW.text = "Wynik: $punkty"

                    Toast.makeText(applicationContext, "Zła odpowiedź!", Toast.LENGTH_SHORT).show()
                }
                true -> {
                    punkty += 1 * mnoznik
                    tvW.text = "Wynik: $punkty"

                    Toast.makeText(applicationContext, "Dobra odpowiedź!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    suspend fun ksiazka() {
        GlobalScope.launch(Dispatchers.Main) {
            tvL.text = getString(R.string.ksiazka)
            delay(flagaTrudnosc)
            tvL.text = null
        }
    }

    suspend fun kamien() {
        GlobalScope.launch(Dispatchers.Main) {
            tvL.text = getString(R.string.kamien)
            delay(flagaTrudnosc)
            tvL.text = null
        }
    }

}