package com.kelompoktmr.wesata.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.kelompoktmr.wesata.databinding.ActivityWhistlistBinding
import com.kelompoktmr.wesata.dbsqlite.WishlistDbHelper

class WishlistActivity : AppCompatActivity() {
    lateinit var binding: ActivityWhistlistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWhistlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0).toString()
        val title = intent.getStringExtra("title").toString()
        val description = intent.getStringExtra("description").toString()
        val context = intent.getStringExtra("context").toString()

        if (context == "add") {
            binding.btnUpdateWhistlist.isVisible = false
            binding.btnDeleteWhistlist.isVisible = false
        } else {
            binding.btnAddWhistlist.isVisible = false
            binding.edWhistlistTitle.setText(title)
            binding.etWhistlistDescription.setText(description)
        }

        binding.btnDeleteWhistlist.setOnClickListener {
            val DbHelper = WishlistDbHelper(this)
            DbHelper.deleteData(id)
            finish()
        }

        binding.btnAddWhistlist.setOnClickListener {
            val DbHelper = WishlistDbHelper(this)
            val title = binding.edWhistlistTitle.text.toString().trim()
            val description = binding.etWhistlistDescription.text.toString().trim()
            DbHelper.insertData(title, description)
            finish()
        }

        binding.btnUpdateWhistlist.setOnClickListener {
            val DbHelper = WishlistDbHelper(this)
            val title = binding.edWhistlistTitle.text.toString().trim()
            val description = binding.etWhistlistDescription.text.toString().trim()
            DbHelper.updateData(id, title, description)
            finish()
        }
    }
}