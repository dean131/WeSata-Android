package com.kelompoktmr.wesata.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompoktmr.wesata.R
import com.kelompoktmr.wesata.dataclass.NotificationData

class NotifAdapter(val dataNotif: ArrayList<NotificationData>) : RecyclerView.Adapter<NotifAdapter.ViewHolderClass>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notif, parent, false)
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {
        return dataNotif.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataNotif[position]
        holder.title.text = currentItem.title
        holder.message.text = currentItem.message
    }

    class ViewHolderClass(itemVew: View): RecyclerView.ViewHolder(itemVew) {
        val title: TextView = itemView.findViewById(R.id.tvNotifItemTitle)
        val message: TextView = itemView.findViewById(R.id.tvNotifItemMessage)
    }

}