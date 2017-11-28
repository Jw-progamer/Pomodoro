package com.br.josias.pomodoro.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.br.josias.pomodoro.R
import com.br.josias.pomodoro.`interface`.CallBack
import com.br.josias.pomodoro.control.Timer
import com.br.josias.pomodoro.entity.Work
import com.orm.SugarRecord


class PomodoroActivity : AppCompatActivity(), CallBack, View.OnClickListener {

    lateinit var mPomodoroTitle: TextView
    lateinit var mClockView: TextView
    lateinit var mStopButton: Button
    lateinit var mPauseButton: Button
    lateinit var mPomodoroStatus: TextView
    lateinit var pomodoroWork: Work
    lateinit var timer: Timer
    var restCount = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro)

        val id = intent.getLongExtra("work", 0)
        pomodoroWork = SugarRecord.findById(Work::class.java, id)

        mPomodoroTitle = findViewById(R.id.pomodoro_title)
        mPomodoroStatus = findViewById(R.id.pomodoro_status)
        mClockView = findViewById(R.id.clocK_view)
        mStopButton = findViewById(R.id.stop_button)
        mPauseButton = findViewById(R.id.pause_button)

        timer = Timer(pomodoroWork.conf.corseTime, pomodoroWork.conf.corseInterval, pomodoroWork.conf.corseRest.toString().toInt(), this)

        mPomodoroTitle.text = pomodoroWork.name
        mPomodoroStatus.text = "Pomodoro"
        mClockView.text = "${pomodoroWork.conf.corseTime.toString()}:00"
        timer.startCounter()

        mPauseButton.setOnClickListener(this)
        mStopButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
     when(view?.id){
         R.id.pause_button -> pauseStop()
         R.id.stop_button -> finish()
     }
    }

    private fun pauseStop() {
        if (timer.stopped)
            timer.restartCounter()
        else
            timer.stopCounter()
    }

    override fun minutePass() {
        runOnUiThread {
            var newTime: Int = 0
            when (mPomodoroStatus.text) {
                "Pomodoro" -> newTime = pomodoroWork.conf.corseTime - 1
                "Interval" -> newTime = pomodoroWork.conf.corseInterval - 1
                "Rest" -> newTime = restCount--
            }
            mClockView.text = "${newTime.toString()}:00"
        }
    }

    override fun cicleOver() {
        runOnUiThread {
            Toast.makeText(this,"Eu chego aqui,",Toast.LENGTH_SHORT)
            mPomodoroStatus.text = "Interval"
            mClockView.text = "${pomodoroWork.conf.corseInterval}:00"
            timer.startInterval()
        }
    }

    override fun intervalOver() {
        runOnUiThread {
            mPomodoroStatus.text = "Pomodoro"
            mClockView.text = "${pomodoroWork.conf.corseTime}:00"
            timer.startCounter()

        }
    }

    override fun restOver() {
        runOnUiThread {
            restCount = 30
            mPomodoroStatus.text = "Pomodoro"
            mClockView.text = "${pomodoroWork.conf.corseTime}:00"
            timer.startCounter()
        }
    }

    override fun isRest() {
        runOnUiThread {
            mPomodoroStatus.text = "Rest"
            mClockView.text = "${restCount}:00"
            timer.startRest()
            true
        }
    }
}
