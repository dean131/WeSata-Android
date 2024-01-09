package com.kelompoktmr.wesata.services

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kelompoktmr.wesata.R
import com.kelompoktmr.wesata.activities.MainActivity
import com.kelompoktmr.wesata.apis.Host
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class NotificationService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startWebSocket()
        return START_STICKY
    }

    private fun startWebSocket() {
        val  serverUri = "ws://${Host.URL.url}/ws/notification/"

        val client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS) // Infinite timeout for reading frames
            .build()

        val request = Request.Builder()
            .url(serverUri)
            .build()

        client.newWebSocket(request, object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val getMessage = JSONObject(text)
                showNotification(getMessage.getString("title"), getMessage.getString("message"))
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                restartService()
            }
        })
    }

    private fun showNotification(title: String, message: String) {
        val CHANNEL_ID = "NotificationChannel1"
        val NOTIFICATION_ID = 1

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("frag", "notificationFragment")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_ic)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }

    }

    private fun restartService() {
        val intent = Intent(this, NotificationService::class.java)
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        restartService()
    }
}