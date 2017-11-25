package com.br.josias.pomodoro.activity

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.br.josias.pomodoro.R
import com.br.josias.pomodoro.entity.Corse
import com.br.josias.pomodoro.entity.Work
import com.orm.SugarRecord

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mAddButton: FloatingActionButton
    lateinit var mListMain: RecyclerView
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLayoutManager = LinearLayoutManager(this)
        mListMain = findViewById(R.id.list_main)

        mListMain.adapter = WorkAdapter(SugarRecord.listAll(Work::class.java))
        mListMain.layoutManager = mLayoutManager

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
                .setTitle("Adiciona atividade")
                .setMessage("digite o nome da atividade quer quer adicionar")
                .setView(input)
                .setPositiveButton("Adicionar", DialogInterface.OnClickListener({
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
