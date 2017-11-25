package com.br.josias.pomodoro.activity

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.br.josias.pomodoro.R
import com.br.josias.pomodoro.entity.Work
import com.orm.SugarRecord


class WorkAdapter(workList: List<Work>) : RecyclerView.Adapter<WorkAdapter.WorkHolder>() {

    private var workList = workList

    override fun getItemCount(): Int {
       return workList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WorkHolder {
        val card = LayoutInflater.from(parent?.context).inflate(R.layout.card_layout,parent,false)
        val holder = WorkHolder(card)
        return holder
    }

    override fun onBindViewHolder(holder: WorkHolder?, position: Int) {
        holder?.bind(workList[position].name)
    }

    inner class WorkHolder(val workView: View) : RecyclerView.ViewHolder(workView) {
        private  var mWorkName: TextView = workView.findViewById(R.id.work_name)

        fun bind(name: String){
            mWorkName.text = name
        }
    }
}

