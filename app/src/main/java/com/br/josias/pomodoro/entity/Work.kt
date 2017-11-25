package com.br.josias.pomodoro.entity

import com.orm.SugarRecord

data class Work(val name:String = "atividade", val conf: Corse = Corse()): SugarRecord() {

}