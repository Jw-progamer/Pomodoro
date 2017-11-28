package com.br.josias.pomodoro.activity

import android.content.DialogInterface
import android.content.Intent
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
import com.br.josias.pomodoro.`interface`.ClickDelegate
import com.br.josias.pomodoro.entity.Corse
import com.br.josias.pomodoro.entity.Work
import com.orm.SugarRecord

class MainActivity : AppCompatActivity(), View.OnClickListener, ClickDelegate {

    lateinit var mAddButton: FloatingActionButton
    lateinit var mListMain: RecyclerView
    lateinit var mListAdapter: WorkAdapter
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLayoutManager = LinearLayoutManager(this)
        mListMain = findViewById(R.id.list_main)

        mListAdapter = WorkAdapter(SugarRecord.listAll(Work::class.java), this)
        mListMain.adapter = mListAdapter
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
                .setPositiveButton("Adicionar", DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int ->
                    if (input.text.toString() != null) {
                        val defaultCorse = Corse()
                        defaultCorse.save()
                        val work = Work(input.text.toString(), defaultCorse)
                        work.save()
                        mListAdapter.updateWork()
                    }
                }))
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener({ dialog: DialogInterface, which: Int ->
                    dialog.cancel()
                }))
                .show()


    }

    override fun onDeleteClick(id: Long) {
        val antiWork = SugarRecord.findById(Work::class.java,id)
        antiWork.delete()
        mListAdapter.updateWork()
    }

    override fun onConfiClick(id: Long) {
        val configCorse = SugarRecord.findById(Work::class.java, id)
        val corseScreen = Intent(this, CorseActivity::class.java)
        corseScreen.putExtra("config", configCorse.conf.id)
        startActivity(corseScreen)
    }

    override fun onStartClick(id: Long) {
        val startWork = SugarRecord.findById(Work::class.java, id)
        val pomodoroScreen = Intent(this, PomodoroActivity::class.java)
        pomodoroScreen.putExtra("work",startWork.id)
        startActivity(pomodoroScreen)
    }

}
