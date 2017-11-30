package com.br.josias.pomodoro.control

import android.util.Log
import com.br.josias.pomodoro.`interface`.CallBack
import com.br.josias.pomodoro.enuns.State
import java.util.concurrent.TimeUnit

class Timer(val time: Int, val interval: Int, val rests: Int, val parent: CallBack) {
    var rest = rests
    var timeCount = time * 60
    var intervalCount = interval * 60
    lateinit var thread: Thread
    var stopped = true
    var paused = false
    var restCount = 30 * 60

    fun startRest() {
        stopped = false
        thread = Thread(Runnable {
            while (!stopped && restCount != 0 && !paused) {
                TimeUnit.SECONDS.sleep(1)
                restCount--
                parent.secondPass()
            }
            if(!paused){
                restCount = 30 * 60
                stopped = true
                parent.restOver()
            }
        })

        thread.start()
    }

    fun startInterval() {

        stopped = false
        thread = Thread(Runnable {
            while (!stopped && intervalCount != 0 && !paused) {
                TimeUnit.SECONDS.sleep(1)
                intervalCount--
                parent.secondPass()
            }
           if(!paused){
               stopped = true
               parent.intervalOver()
           }
        })

        thread.start()

    }

    fun startCounter() {

        stopped = false
        rest--
        thread = Thread(Runnable {
            while (!stopped && timeCount != 0 && !paused) {
                TimeUnit.SECONDS.sleep(1)
                timeCount--
                parent.secondPass()
            }
            if (!paused) {
                stopped = true
                timeCount = time * 60
                if (rest != 0) {
                    parent.cicleOver()
                } else {
                    rest = rests
                    parent.isRest()
                }
            }
        })

        thread.start()

    }

    fun pauseCounter(){
        if(!paused){
            paused = true
            thread.join()
        }
    }

    fun stopCounter() {
        if (!stopped) {
            stopped = true
            thread.join()
        }
    }

    fun restartCounter(state: String) {
        if (paused) {
            paused = false
            when(state){
                State.POMODORO.toString() -> startCounter()
                State.INTERVAlO.toString() -> startInterval()
                State.DESCANÇO.toString() -> startRest()
            }
        }
    }
}