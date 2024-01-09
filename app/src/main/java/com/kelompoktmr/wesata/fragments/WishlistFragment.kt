package com.kelompoktmr.wesata.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompoktmr.wesata.activities.WishlistActivity
import com.kelompoktmr.wesata.adapters.WishlistAdapter
import com.kelompoktmr.wesata.databinding.FragmentWishlistBinding
import com.kelompoktmr.wesata.dbsqlite.WishlistDbHelper


class WishlistFragment : Fragment() {
    lateinit var binding: FragmentWishlistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.fabAddWishlist.setOnClickListener {
            val intent = Intent(activity, WishlistActivity::class.java)
            intent.putExtra("context", "add")
            startActivity(intent)
        }

        loadData()
        return view
    }

    private fun loadData() {
        val dbHelper = WishlistDbHelper(requireActivity())
        val dataWishlist = dbHelper.readData()
        Log.i("RESSSS", dataWishlist.toString())
        binding.rvWhislist.layoutManager = LinearLayoutManager(activity)
        binding.rvWhislist.adapter = WishlistAdapter(dataWishlist)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

}