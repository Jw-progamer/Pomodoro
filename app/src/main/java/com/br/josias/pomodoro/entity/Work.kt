package com.br.josias.pomodoro.entity

import com.orm.SugarRecord

/**
 * Created by josias on 22/11/17.
 */
data class Work(val name:String, val conf: Corse): SugarRecord<Work>() {
}