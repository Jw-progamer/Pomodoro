package com.br.josias.pomodoro.activity

import android.graphics.Color
import android.media.MediaPlayer
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
import com.br.josias.pomodoro.enuns.State
import com.orm.SugarRecord


class PomodoroActivity : AppCompatActivity(), CallBack, View.OnClickListener {

    lateinit var mPomodoroTitle: TextView
    lateinit var mClockView: TextView
    lateinit var mStopButton: Button
    lateinit var mPauseButton: Button
    lateinit var mPomodoroStatus: TextView
    lateinit var mPomodoroPaused: TextView

    lateinit var pomodoroWork: Work
    lateinit var timer: Timer

    var corseConter = 0
    var intervalConter = 0

    var restCount = 30 * 60

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
        mPomodoroPaused = findViewById(R.id.pomodoro_paused)

        timer = Timer(pomodoroWork.conf.corseTime, pomodoroWork.conf.corseInterval, pomodoroWork.conf.corseRest.toString().toInt(), this)

        corseConter = pomodoroWork.conf.corseTime * 60
        intervalConter = pomodoroWork.conf.corseInterval * 60

        mPomodoroTitle.text = pomodoroWork.name
        mPomodoroStatus.text = State.POMODORO.toString()
        mClockView.text = "${pomodoroWork.conf.corseTime.toString()}:00"
        timer.startCounter()

        mPauseButton.setOnClickListener(this)
        mStopButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.pause_button -> pauseStop()
            R.id.stop_button -> finish()
        }
    }


    private fun pauseStop() {
        if (timer.paused) {
            timer.restartCounter(mPomodoroStatus.text.toString())
            mPomodoroPaused.text = ""
        } else {
            mPomodoroPaused.text = "Pausado"
            timer.pauseCounter()
        }
    }

    override fun finish() {
        super.finish()
        timer.stopCounter()
    }

    fun siren() {
        val sound = MediaPlayer.create(this, R.raw.finish)
        sound.start()
    }

    override fun secondPass() {
        runOnUiThread {
            var newTimeMinute: Int = 0
            var newTimeSecond: Int = 0
            when (mPomodoroStatus.text) {
                State.POMODORO.toString() -> {
                    corseConter--
                    newTimeMinute = corseConter / 60
                    newTimeSecond = corseConter % 60
                }

                State.INTERVAlO.toString() -> {
                    intervalConter--
                    newTimeMinute = intervalConter / 60
                    newTimeSecond = intervalConter % 60
                }

                State.DESCANÇO.toString() -> {
                    restCount--
                    newTimeMinute = restCount / 60
                    newTimeSecond = restCount % 60
                }
            }

            mClockView.text = "${
            if (newTimeMinute.toString().length == 2)
                newTimeMinute.toString()
            else
                "0"+newTimeMinute.toString()
            }:${
            if(newTimeSecond.toString().length == 2)
                newTimeSecond.toString()
            else
                "0"+newTimeSecond.toString()
            }"
        }
    }

    override fun cicleOver() {
        siren()
        runOnUiThread {
            mPomodoroStatus.text = State.INTERVAlO.toString()
            mPomodoroStatus.setTextColor(Color.YELLOW)
            intervalConter = pomodoroWork.conf.corseInterval * 60
            mClockView.text = "${pomodoroWork.conf.corseInterval}:00"
            timer.startInterval()
        }
    }

    override fun intervalOver() {
        siren()
        runOnUiThread {
            mPomodoroStatus.text = State.POMODORO.toString()
            mPomodoroStatus.setTextColor(Color.GREEN)
            corseConter = pomodoroWork.conf.corseTime * 60
            mClockView.text = "${pomodoroWork.conf.corseTime}:00"
            timer.startCounter()

        }
    }

    override fun restOver() {
        siren()
        runOnUiThread {
            restCount = 30 * 60
            mPomodoroStatus.text = State.POMODORO.toString()
            mPomodoroStatus.setTextColor(Color.GREEN)
            corseConter = pomodoroWork.conf.corseTime
            mClockView.text = "${pomodoroWork.conf.corseTime}:00"
            timer.startCounter()
        }
    }

    override fun isRest() {
        runOnUiThread {
            mPomodoroStatus.text = State.DESCANÇO.toString()
            mPomodoroStatus.setTextColor(Color.BLUE)
            mClockView.text = "${restCount}:00"
            timer.startRest()
        }
    }
}
