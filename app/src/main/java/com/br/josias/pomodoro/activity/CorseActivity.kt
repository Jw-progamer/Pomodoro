package com.br.josias.pomodoro.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.br.josias.pomodoro.R
import com.br.josias.pomodoro.entity.Corse
import com.orm.SugarRecord

class CorseActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mTimeSeek: SeekBar
    lateinit var mIntervalSeek: SeekBar
    lateinit var mRestNunber: EditText
    lateinit var mSaveButton: Button
    lateinit var corseConfig: Corse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corse)

        mTimeSeek = findViewById(R.id.time_seek)
        mIntervalSeek = findViewById(R.id.interval_seek)
        mRestNunber = findViewById(R.id.rest_number)
        mSaveButton = findViewById(R.id.save_button)

        val id = intent.getLongExtra("config",0)
        corseConfig = SugarRecord.findById(Corse::class.java, id)

        mTimeSeek.progress = corseConfig.corseTime
        mIntervalSeek.progress = corseConfig.corseInterval

        var number: Editable = Editable.Factory().newEditable(corseConfig.corseRest.toString())

        mRestNunber.text = number

        mSaveButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
       when(view?.id){
           R.id.save_button -> updateCorseConfig()
       }
    }

    private fun updateCorseConfig() {
        corseConfig.corseTime = mTimeSeek.progress
        corseConfig.corseInterval = mIntervalSeek.progress
        corseConfig.corseRest = mRestNunber.text.toString().toInt()
        corseConfig.save()

        finish()
    }
}
