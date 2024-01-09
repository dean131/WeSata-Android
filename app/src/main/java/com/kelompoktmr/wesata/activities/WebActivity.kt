package com.kelompoktmr.wesata.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompoktmr.wesata.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webUrl = intent.getStringExtra("url").toString()
        binding.webView.loadUrl(webUrl)
        binding.webView.settings.javaScriptEnabled = true
    }
}