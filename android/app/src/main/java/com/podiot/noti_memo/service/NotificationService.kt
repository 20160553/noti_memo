package com.podiot.noti_memo.service

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import com.podiot.noti_memo.R
import com.podiot.noti_memo.receivers.NotificationReplyReceiver
import com.podiot.noti_memo.receivers.StopBroadcastReceiver

class NotificationService(
    private val context: Context,
    pendingIntent: PendingIntent
) {

    lateinit var launcher: ManagedActivityResultLauncher<String, Boolean>
    private val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
    private val builder = when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        true -> NotificationCompat.Builder(
            context,
            context.resources.getString(R.string.channel_id)
        )

        false -> {
            Log.d("sdk version", "< 26")
            NotificationCompat.Builder(context).setPriority(NotificationCompat.PRIORITY_LOW)
        }
    }

    init {
        val notificationStopIntent = Intent(context, StopBroadcastReceiver::class.java)
        val notificationStopAction = PendingIntent.getBroadcast(
            context,
            context.resources.getInteger(R.integer.notification_stop_request_code),
            notificationStopIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Key for the string that's delivered in the action's intent.
        var replyLabel: String = context.resources.getString(R.string.notification_reply_label)
        var remoteInput: RemoteInput = RemoteInput.Builder(context.resources.getString(R.string.notification_reply_key)).run {
            setLabel(replyLabel)
            build()
        }

        // Build a PendingIntent for the reply action to trigger.
        val replyIntent = Intent(context, NotificationReplyReceiver::class.java)
        val replyPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context,
                context.resources.getInteger(R.integer.notification_reply_request_code),
                replyIntent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        var replyAction: NotificationCompat.Action =
            NotificationCompat.Action.Builder(
                0,
                "메모 작성"
                , replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build()

        builder
            .setSmallIcon(android.R.drawable.ic_input_get)
            .setContentTitle("아래로 내려 상단바 메모를 작성하세요")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // 잠금화면에서 표시
            // Set the intent that fires when the user taps the notification.
            .setContentIntent(pendingIntent)
            .addAction(replyAction)
            .addAction(
                0, "종료", notificationStopAction
            )
            .setOngoing(true)

    }

    fun checkNotificationPermission(): Boolean =
        when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true -> {
                /** 권한이 이미 있는 경우 **/
                if (
                    ContextCompat.checkSelfPermission(
                        context,
                        notificationPermission
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    true
                }
                /** 권한이 없는 경우 **/
                else {
                    launcher.launch(notificationPermission)
                    false
                }
            }

            false -> true
        }

    fun showNotification(notificationContentText: String) {
        with(NotificationManagerCompat.from(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!checkNotificationPermission())
                    return
            }
            notify(
                context.resources.getInteger(R.integer.notification_id),
                builder
                    .setContentText(notificationContentText)
                    .build()
            )
        }
    }

}