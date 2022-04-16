/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * NO ALTERE LOS NOMBRES DE NINGUNA VARIABLE O VALOR O SUS VALORES INICIALES.
     *
     * Se espera que cualquier cosa etiquetada como var en lugar de val se cambie en las funciones, pero NO
     * modifique sus valores iniciales declarados aquí, esto podría causar que la aplicación no funcione correctamente.
     */
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT representa el estado "recoger limón"
    private val SELECT = "select"
    // SQUEEZE representa el estado "exprimir limón"
    private val SQUEEZE = "squeeze"
    // DRINK representa el estado "beber limonada"
    private val DRINK = "drink"
    // RESTART representa el estado en el que se ha bebido la limonada y el vaso está vacío
    private val RESTART = "restart"
    // Por defecto el estado para seleccionar
    private var lemonadeState = "select"
    // Predeterminado lemonSize a -1
    private var lemonSize = -1
    // Predeterminar el squeezeCount a -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === NO ALTERAR EL CÓDIGO EN LA SIGUIENTE DECLARACIÓN IF ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF DECLARACIÓN ===

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            // TODO: llamar al método que maneja el estado cuando se hace clic en la imagen
            clickLemonImage()

        }
        lemonImage!!.setOnLongClickListener {
            // TODO: reemplazar 'falso' con una llamada a la función que muestra el recuento de apretones

            false

        }
    }

    /**
     * === NO ALTERAR ESTE MÉTODO ===
     *
     * Este método guarda el estado de la aplicación si se pone en segundo plano.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Hacer clic provocará una respuesta diferente según el estado.
     * Este método determina el estado y procede con la acción correcta.
     */

    private fun clickLemonImage() {
        // TODO: use una declaración condicional como 'if' o 'when' para rastrear el LemonadeState
        // cuando se hace clic en la imagen, es posible que debamos cambiar el estado al siguiente paso en el
        //  limonada progresando (o al menos hacer algunos cambios al estado actual en el
        // caso de exprimir el limón). Eso debe hacerse en esta declaración condicional.
        when (lemonadeState) {
            SELECT -> {
                // TODO: Cuando se hace clic en la imagen en el estado SELET, el estado debe convertirse en SQUEEZE
                // - La variable lemonSize debe establecerse utilizando el método 'pick()' en la clase LemonTree
                // - SqueezeCount debería ser 0 ya que todavía no hemos exprimido ningún limón.

                lemonadeState = SQUEEZE
                lemonSize = lemonTree.pick()
                squeezeCount = 0
            }
            SQUEEZE ->{
                // TODO: Cuando se hace clic en la imagen en el estado SQUEEZE, squeezeCount debe ser
                // INCREMENTADO en 1 y lemonSize debe DISMINUIR en 1.
                // - Si lemonSize ha llegado a 0, se ha exprimido y el estado debería convertirse en BEBIDA
                // - Además, lemonSize ya no es relevante y debe establecerse en -1
                squeezeCount += 1
                lemonSize -= 1
                if(lemonSize==0){
                    lemonadeState = DRINK
                    lemonSize = -1
                }
                showSnackbar()
            }
            DRINK ->{
                // TODO: Cuando se hace clic en la imagen en el estado DRINK, el estado debe convertirse en RESTART
                lemonadeState =RESTART

            }
            RESTART ->{
                // TODO: Cuando se hace clic en la imagen en el estado REINICIAR, el estado debe convertirse en SELECCIONAR
                lemonadeState = SELECT
            }
            // TODO: por último, antes de que termine la función, debemos configurar los elementos de vista para que el
            // La interfaz de usuario puede reflejar el estado correct
        }
        setViewElements()
    }


    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.text_action)
        // TODO: configurar un condicional que rastree el LemonadeState

        // TODO: para cada estado, textAction TextView debe establecerse en la cadena correspondiente de
        // el archivo de recursos de cadena. Las cadenas se nombran para que coincidan con el estado.

        // TODO: Además, para cada estado, lemonImage debe establecerse en el correspondiente
        // extraíble de los recursos extraíbles. Los dibujables tienen los mismos nombres que las cadenas.
        // pero recuerda que son dibujables, no cadenas.
        when(lemonadeState){
            SELECT -> {
                textAction.text = resources.getString(R.string.lemon_select)
                lemonImage?.setImageResource(R.drawable.lemon_tree)
            }
            SQUEEZE ->{
                textAction.text = resources.getString(R.string.lemon_squeeze)
                lemonImage?.setImageResource(R.drawable.lemon_squeeze)
            }
            DRINK ->{
                textAction.text = resources.getString(R.string.lemon_drink)
                lemonImage?.setImageResource(R.drawable.lemon_drink)
            }
            RESTART ->{
                textAction.text = resources.getString(R.string.lemon_empty_glass)
                lemonImage?.setImageResource(R.drawable.lemon_restart)
            }
        }
    }


    /**
     * === NO ALTERAR ESTE MÉTODO ===
     *
     * Hacer clic largo en la imagen del limón mostrará cuántas veces se ha exprimido el limón.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}
/**
 * Una clase de árbol de limón con un método para "recoger" un limón. El "tamaño" del limón es aleatorio.
 * y determina cuántas veces se debe exprimir un limón antes de obtener limonada.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
