package com.grass.hellonotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MainActivity : AppCompatActivity() {
    companion object {
        var CHANNEL_ID = "grass"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.notify_btn).setOnClickListener {
            showNotification(this@MainActivity)
        }
    }

    /**
     * Lollipop系统后，通知栏小图标要使用纯白+透明的图标模式。
     * Build Target < 21也OK。
     */
    fun getSuitableSmallIcon(): Int {
        if (MobileUtil.isHuawei()) {
            return R.mipmap.emui_icon
        }
        return if (MobileUtil.isOppo()) {
            R.mipmap.emui_icon
        } else R.mipmap.icon_white
        return R.drawable.kb_icon;
    }

    var CALENDAR_ID: String? = "channel_01"

    private fun showNotification(context: Context) {
        var notificationManager = NotificationManagerCompat.from(this@MainActivity)
        val notification: Notification
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(makeNotificationChannel())
        }
        builder = NotificationCompat.Builder(context, CALENDAR_ID!!)
        val pendingIntent = PendingIntent.getActivity(context, 11,
                Intent(context, SecondActivity::class.java), 0)
        notification = builder.setSmallIcon(getSuitableSmallIcon())
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun makeNotificationChannel(): NotificationChannel {
        val notificationChannel = NotificationChannel(CALENDAR_ID, "123",
                NotificationManager.IMPORTANCE_DEFAULT)
        // 设置渠道描述
        notificationChannel.description = "测试通知组"
        // 是否绕过请勿打扰模式
        notificationChannel.canBypassDnd()
        // 设置绕过请勿打扰模式
        notificationChannel.setBypassDnd(true)
        // 桌面Launcher的消息角标
        notificationChannel.canShowBadge()
        // 设置显示桌面Launcher的消息角标
        notificationChannel.setShowBadge(true)
        // 设置通知出现时声音，默认通知是有声音的
        notificationChannel.setSound(null, null)
        // 设置通知出现时的闪灯（如果 android 设备支持的话）
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        // 设置通知出现时的震动（如果 android 设备支持的话）
        notificationChannel.enableVibration(true)
        notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400,
                300, 200, 400)
        return notificationChannel;
    }

}
