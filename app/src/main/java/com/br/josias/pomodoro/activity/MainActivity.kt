package com.br.josias.pomodoro.activity

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import com.br.josias.pomodoro.R
import com.br.josias.pomodoro.entity.Corse
import com.br.josias.pomodoro.entity.Work
import com.orm.SugarRecord

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mAddButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAddButton = findViewById(R.id.add_button)
        mAddButton.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_button -> addWork()
        }
    }

    fun addWork() {
        val input = EditText(this)
        val dialog = AlertDialog.Builder(this)
                .setTitle("Adicionar atividade")
                .setMessage("digite o nome da atividade quer quer adicionar")
                .setView(input)
                .setPositiveButton("Adicionat", DialogInterface.OnClickListener({
                  dialog:DialogInterface, which: Int ->
                    if(input.text.toString() != null){
                        val defaultCorse = Corse()
                        defaultCorse.save()
                        val work = Work(input.text.toString(), defaultCorse)
                        work.save()
                    }
                }))
                .setNegativeButton("Cancelar",DialogInterface.OnClickListener({
                    dialog:DialogInterface, which: Int ->
                    dialog.cancel()
                }))
                .show()



    }
}
