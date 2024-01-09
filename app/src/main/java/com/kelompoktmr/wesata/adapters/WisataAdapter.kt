package com.kelompoktmr.wesata.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelompoktmr.wesata.activities.DetailDestinationActivity
import com.kelompoktmr.wesata.R
import com.kelompoktmr.wesata.dataclass.WisataData


class WisataAdapter(val wisataArrayList: ArrayList<WisataData>): RecyclerView.Adapter<WisataAdapter.ViewHolderClass> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wisata, parent, false)
        return ViewHolderClass(view)
    }

    override fun getItemCount(): Int {
        return wisataArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = wisataArrayList[position]
        holder.title.text = currentItem.Title
        holder.description.text = currentItem.Description
        Glide.with(holder.itemView.context).load(currentItem.Image).into(holder.image);

        val idWisata = currentItem.id.toString()
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailDestinationActivity::class.java)
            intent.putExtra("idWisata", idWisata)
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvWisataItemTitle)
        val description: TextView = itemView.findViewById(R.id.tvWisataItemDescription)
        val image: ImageView = itemView.findViewById(R.id.ivWisataItem)
    }


}