package com.br.josias.pomodoro.entity

import com.orm.SugarRecord

/**
 * Created by josias on 22/11/17.
 */
data class Corse(var corseTime:Int = 20, var corseInterval:Int = 5, var corseRest: Int = 4): SugarRecord<Corse>() {
}