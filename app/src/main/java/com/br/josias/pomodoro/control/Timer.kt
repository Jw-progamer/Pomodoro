package com.br.josias.pomodoro.control

import com.br.josias.pomodoro.`interface`.CallBack
import java.util.concurrent.TimeUnit

/**
 * Created by josias on 25/11/17.
 */
class Timer(val time: Int, val interval: Int, val rests: Int, val parent: CallBack) {
    var rest = rests
    var timeCount = time
    var intervalCount = interval
    lateinit var thread: Thread
    var stopped = true
    var lock = true

    fun startRest() {
        stopped = false
        var count = 30
        thread = Thread(Runnable {
            while (!stopped) {
                TimeUnit.MINUTES.sleep(1)
                count -= 1
                parent.minutePass()
            }
            stopped = true
            parent.restOver()
        })

        thread.start()
    }

    fun startInterval() {

        stopped = false
        intervalCount = interval
        thread = Thread(Runnable {
            while (!stopped) {
                TimeUnit.MINUTES.sleep(1)
                intervalCount -= 1
                parent.minutePass()
            }
            stopped = true
            parent.intervalOver()
        })

        thread.start()

    }

    fun startCounter() {

        stopped = false
        rest -= 1
        timeCount = time
        thread = Thread(Runnable {
            while (!stopped) {
                TimeUnit.MINUTES.sleep(1)
                timeCount -= 1
                parent.minutePass()
            }
            stopped = true
            if (rest != 0) {
                parent.cicleOver()
            } else {
                rest = rests
                parent.isRest()
            }
        })

        thread.start()

    }

    fun stopCounter() {
        if (!stopped) {
            stopped = true
            thread.join()
        }
    }

    fun restartCounter() {
        if (stopped) {
            stopped = false
            startCounter()
        }
    }
}