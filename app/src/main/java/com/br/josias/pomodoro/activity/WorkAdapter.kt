package com.br.josias.pomodoro.activity

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.br.josias.pomodoro.R
import com.br.josias.pomodoro.`interface`.ClickDelegate
import com.br.josias.pomodoro.entity.Work
import com.orm.SugarRecord


class WorkAdapter(workList: List<Work>, val context: Context) : RecyclerView.Adapter<WorkAdapter.WorkHolder>() {

    private var workList = workList

    fun updateWork(){
        workList = SugarRecord.listAll(Work::class.java)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return workList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WorkHolder {
        val card = LayoutInflater.from(parent?.context).inflate(R.layout.card_layout,parent,false)
        val holder = WorkHolder(card,context as ClickDelegate)
        return holder
    }

    override fun onBindViewHolder(holder: WorkHolder?, position: Int) {
        holder?.bind(workList[position].name, workList[position].id)
    }

    inner class WorkHolder(val workView: View,val parent: ClickDelegate) : RecyclerView.ViewHolder(workView), View.OnClickListener {

        private  val mWorkName: TextView = workView.findViewById(R.id.work_name)
        private val mConfigButon: Button = workView.findViewById(R.id.config_button)
        private val mStartButton: Button = workView.findViewById(R.id.start_button)
        private val mRemoveButton: Button = workView.findViewById(R.id.remove_button)

        init {
            mConfigButon.setOnClickListener(this)
            mStartButton.setOnClickListener(this)
            mRemoveButton.setOnClickListener(this)
        }

        private var id: Long = 0
        fun bind(name: String, workId:Long){
            mWorkName.text = name
            id = workId
        }

        override fun onClick(view: View?) {
            when(view?.id){
                R.id.config_button -> parent.onConfiClick(id)

                R.id.remove_button -> parent.onDeleteClick(id)

                R.id.start_button -> parent.onStartClick(id)
            }
        }


    }
}

