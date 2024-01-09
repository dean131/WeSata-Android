package com.kelompoktmr.wesata.activities


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kelompoktmr.wesata.R
import com.kelompoktmr.wesata.databinding.ActivitySmsBinding


class SMSActivity : AppCompatActivity() {
    lateinit var binding: ActivitySmsBinding
    private val CHANNEL_ID = "channel_id_example_01"
    val NOTIFICATION_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.btnSMSActivitySend.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendMessage()
            } else {
                requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 1)
            }
        }
    }

    private fun sendMessage() {
        val phoneNumber = binding.etSMSActivityPhoneNumber.text.toString().trim()
        val message = binding.etSMSActivityMessage.text.toString().trim()

        if (phoneNumber.isEmpty()) {
            binding.etSMSActivityPhoneNumber.error = "Nomor telepon jangan kosong"
            binding.etSMSActivityPhoneNumber.requestFocus()
            return
        }

        if (message.isEmpty()) {
            binding.etSMSActivityMessage.error = "Pesan jangan kosong"
            binding.etSMSActivityMessage.requestFocus()
            return
        }

//        val smsManager = SmsManager.getDefault()
//        smsManager.sendTextMessage(phoneNumber, null, message, null, null)

        sendNotification()

        binding.etSMSActivityMessage.text.clear()
        binding.etSMSActivityPhoneNumber.text.clear()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "SMS Notification"
            val descriptionText = "Ini adalah notifikasi untuk SMS"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_ic)
            .setContentTitle("Pengiriman SMS")
            .setContentText("SMS berhasil dikirim")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                notify(NOTIFICATION_ID, builder.build())
            } else {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }
}