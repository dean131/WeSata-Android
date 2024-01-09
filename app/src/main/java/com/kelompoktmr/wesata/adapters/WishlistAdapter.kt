package com.kelompoktmr.wesata.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompoktmr.wesata.R
import com.kelompoktmr.wesata.activities.WishlistActivity
import com.kelompoktmr.wesata.dataclass.WishlistData

class WishlistAdapter(val dataWishlist: ArrayList<WishlistData>) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolderClass>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistAdapter.ViewHolderClass {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_wishlist, parent, false)
        return ViewHolderClass(view)
    }

    override fun onBindViewHolder(holder: WishlistAdapter.ViewHolderClass, position: Int) {
        val currentItem = dataWishlist[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WishlistActivity::class.java)
            intent.putExtra("id", currentItem.id)
            intent.putExtra("title", currentItem.title)
            intent.putExtra("description", currentItem.description)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataWishlist.size
    }

    inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.item_whislist_title)
        val description = itemView.findViewById<TextView>(R.id.item_whislist_description)
    }
}