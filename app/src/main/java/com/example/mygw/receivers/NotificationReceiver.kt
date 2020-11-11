package com.example.mygw.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast

class NotificationReceiver : BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_MESSAGE = "Notification message"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.getStringExtra(NOTIFICATION_MESSAGE) != null) {
            context?.toast(intent.getStringExtra(NOTIFICATION_MESSAGE))
        }
    }
}