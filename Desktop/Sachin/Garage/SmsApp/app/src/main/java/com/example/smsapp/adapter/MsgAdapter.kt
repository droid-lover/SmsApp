package com.example.smsapp.adapter

/**
 * Created by Sachin
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smsapp.R
import com.example.smsapp.models.Msg
import kotlinx.android.synthetic.main.item_msg_layout.view.*


class MsgAdapter(private val messages: ArrayList<Msg>) : androidx.recyclerview.widget.RecyclerView.Adapter<MsgAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_msg_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(messages[position])
    }


    override fun getItemCount(): Int {
        return messages.size
    }


    inner class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(msg: Msg) {
            tvNumber.text =msg.number
            tvMsg.text=msg.msg
        }
        private val tvNumber = itemView.tvNumber!!
        private val tvMsg = itemView.tvMsg!!
        private val parentConstraint = itemView.parentConstraint!!
    }

}
