package com.example.mygw.fragments

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.mygw.R
import com.example.mygw.receivers.NotificationReceiver
import kotlinx.android.synthetic.main.fragment_notification.*


class NotificationFragment : BaseFragment() {

    private var notificationManager: NotificationManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        btnNotification.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, NotificationReceiver::class.java)
            intent.putExtra(NotificationReceiver.NOTIFICATION_MESSAGE, "My notification")
            val pendingIntent = PendingIntent.getBroadcast(activity,0,intent,0)

            val notificationID = 101
            val channelID = "com.example.mygw"

            val notification = Notification.Builder(activity,
                channelID)
                .setContentTitle("Example Notification")
                .setContentText("This is an  example notification.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(channelID)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager?.notify(notificationID, notification)

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = "com.example.mygw"
        val name = "Simple Notification"
        val description = "Notification description"

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED

        notificationManager?.createNotificationChannel(channel)

    }

}