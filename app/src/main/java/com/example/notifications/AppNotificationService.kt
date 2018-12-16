package com.example.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppNotificationService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.i("app token", token)
        //todo inviare ad un server personale per avere logica personalizzata
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        sendNotification(
            title = remoteMessage.data.getValue("title"),
            body  = remoteMessage.data.getValue("message")
        )
    }

    private fun sendNotification(title: String, body: String) {

        val notification = NotificationCompat.Builder(this, "app_id")
            .setSmallIcon(R.drawable.ic_notify)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Da Oreo, definire un channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "app_id",
                "Titolo del canale",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notification.build())
    }

}
