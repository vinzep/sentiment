package com.example.sentiments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null
    private var textSentiment: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSpeak = this.button_speak
        editText = this.edittext_input
        textSentiment = this.textViewSentiment

        buttonSpeak!!.isEnabled = false;
        tts = TextToSpeech(this, this)

        buttonSpeak!!.setOnClickListener { speakOut() }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut() {
        var text = editText!!.text.toString().toLowerCase()

        var texto = text.split(" ")
        val set = mutableSetOf<String>()


        val positivas = "Abundante , Feliz , encantado , Admirable , Afectuoso , Agradable , Altruista , Amigo , Amistad , Amor , Amoroso , Apreciable , Autoestima , Ayuda , Beneficioso , Benéfico , Bonachón , Bondadoso , Bondadoso , Bonito , Bueno , Candoroso , Cariño , Cariñoso , Caritativo , Claro , Coherente , Compañerismo , Compañero , Compasivo , Comprensión , Comprensivo , Conveniente , Cordial , Correcto , Dadivoso , Decente , Desinteresado , Desprendido , Digno , Digno , Diversión , Divertido , Divertido , Equilibrado , Equitativo , Esperanza , Espléndido , Espontáneo , Estimable , Excelente , Excelente , Fortaleza , Franco , Futuro , Generoso , Generoso , Generoso , Genuino , Grandioso , Hermoso , Honesto , Honesto , Honrado , Honroso , Humano , Humilde , Ingenuo , Inocente , Íntegro , Justo , Leal , Leal , Magnifico , Maravilloso , Maravilloso , Mejor , Natural , Noble , Optimista , Óptimo , Paciente , Palabras , Piadoso , Positivo , Productivo , Propicio , Real , Reciprocidad , Recto , Respeto , Respetuoso , Sensible , Simple , Sincero , Sincero , Sincero , Sublime , Tierno , Tolerante , Transparente , Unidad , Valiente , Verdadero".toLowerCase().split(" , ")
        val negativas = "nadie triste enojado desnimado nada nunca jamás ninguno ninguna ningunos ningunas tampoco ni".split(" ")

        val tama = texto.size
        var pos = 0
        var neg = 0
        println("tamaño de tokens= "+ tama)

        for (i in texto){
            println(i)
            if(positivas.contains(i))
                pos++
            if(negativas.contains(i))
                neg++
        }

        var menu = ""
        if (pos>neg){
            menu = "El texto es Positivo te sugerimos tomar vino tinto o cerveza para relajarte, una sopa de  pasta con camarones y Pollo a la naranja"
            textSentiment!!.setText(menu)
        }
        else if (neg>pos){
            menu = "El texto es Negativo te sugerimos tomar tequila, whisky para darte ánimos, Sopa de Pollo con tallarines, y Ensalada de salmón con  vinagreta  "
            textSentiment!!.setText(menu)
        }
        else if (neg == pos){
            menu= "El texto es Neutral te sugerimos tomar café para dar energía, una sopa de letras, consome y pollo con mole rojo porque mexicano "
            textSentiment!!.setText(menu)
        }


        tts!!.speak(menu, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}