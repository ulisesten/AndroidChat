package com.example.app1tabbed.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.app1tabbed.R
import kotlinx.android.synthetic.main.chat_message_incoming_view.view.*


data class Message(val type: String, val name: String, val message: String, val time: String, val imageUrl: String){}

class MainAdapter( private val elementos: MutableList<Message>): RecyclerView.Adapter<MyViewHolder>() {

    //val mContext = context
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater: LayoutInflater
        var cellForRow: View? = null

        if( viewType == VIEW_TYPE_MESSAGE_SENT ) {
            layoutInflater = LayoutInflater.from(parent.context)
            cellForRow =
                layoutInflater.inflate(R.layout.chat_message_sender_view, parent, false)
        }
        else if ( viewType == VIEW_TYPE_MESSAGE_RECEIVED ){
            layoutInflater = LayoutInflater.from(parent.context)
            cellForRow =
                layoutInflater.inflate(R.layout.chat_message_incoming_view, parent, false)
        }
        return MyViewHolder(cellForRow!!)
    }

    override fun getItemCount(): Int {
        return elementos.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = elementos[position]

        if( message.type == "outgoing" )
            return 1
        else
            return 2
        //return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: MyViewHolder, pos: Int) {
        holder.itemView.text_message_body.text = elementos[pos].message
        holder.itemView.text_message_time.text = elementos[pos].time


        if( holder.itemViewType == VIEW_TYPE_MESSAGE_RECEIVED){

            holder.itemView.text_message_name.text = elementos[pos].name

            val imgView = holder.itemView.image_message_profile

            val mContext = holder.itemView.context

            Glide.with(mContext).load(elementos[pos].imageUrl).into(imgView)

            holder.itemView.setOnClickListener {

                Toast.makeText(mContext, "Por implementar", Toast.LENGTH_SHORT).show()

            }
        }




    }


}

class MyViewHolder(v: View): RecyclerView.ViewHolder(v) {

}