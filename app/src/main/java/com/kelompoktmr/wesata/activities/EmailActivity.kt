package com.kelompoktmr.wesata.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kelompoktmr.wesata.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendEmail.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val recipient = arrayOf(binding.etEmailRecipient.text.toString().trim())
        val subject = binding.etEmailSubject.text.toString().trim()
        val message = binding.etEmailMessage.text.toString().trim()

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.setData(Uri.parse("mailto:"))
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipient)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, message)
        emailIntent.setType("message/rfc822")
        startActivity(Intent.createChooser(emailIntent, "Email"))

        Toast.makeText(this, "Email Terkirim", Toast.LENGTH_SHORT).show()
    }
}