package com.br.josias.pomodoro.`interface`

/**
 * Created by josias on 25/11/17.
 */
interface ClickDelegate {
    fun onDeleteClick(id: Long)

    fun onConfiClick(id: Long)

    fun onStartClick(id: Long)
}