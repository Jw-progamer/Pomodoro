package com.br.josias.pomodoro.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by josias on 22/11/17.
 */
class PomodoroDatabase(val context: Context): SQLiteOpenHelper(context, "pomodoro_mobile.db",null,1){
    override fun onCreate(db: SQLiteDatabase) {
        executeSql(db,"db/pomodoro_db_create.sql")
    }

    override fun onUpgrade(db: SQLiteDatabase, new: Int, older: Int) {
        executeSql(db,"db/pomodoro_db_update.sql")
    }

    private fun executeSql(db: SQLiteDatabase, patch: String){
        val sql = context.assets.open(patch)
        sql.bufferedReader().forEachLine { if (!it.trim().isEmpty()) db.execSQL(it) }
    }

}