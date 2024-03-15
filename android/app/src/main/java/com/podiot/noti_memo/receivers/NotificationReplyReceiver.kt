package com.podiot.noti_memo.receivers

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.podiot.noti_memo.MainActivity
import com.podiot.noti_memo.R
import com.podiot.noti_memo.domain.model.NoteModel
import com.podiot.noti_memo.domain.usecase.CreateNoteUsecase
import com.podiot.noti_memo.domain.usecase.GetNoteUsecase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReplyReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var createNoteUsecase: CreateNoteUsecase

    @Inject
    lateinit var getNoteUsecase: GetNoteUsecase

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val repliedText = RemoteInput.getResultsFromIntent(intent)?.getCharSequence(
            context.getString(
                R.string.notification_reply_key
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            createNoteUsecase.insertNote(NoteModel(content = "$repliedText"))
        }

        val builder = when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            true -> NotificationCompat.Builder(
                context,
                context.resources.getString(R.string.channel_id)
            )

            false -> {
                NotificationCompat.Builder(context).setPriority(NotificationCompat.PRIORITY_LOW)
            }
        }

        val notificationStopIntent = Intent(context, StopBroadcastReceiver::class.java)
        val notificationStopAction = PendingIntent.getBroadcast(
            context,
            context.resources.getInteger(R.integer.notification_stop_request_code),
            notificationStopIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Key for the string that's delivered in the action's intent.
        var replyLabel: String = context.resources.getString(R.string.notification_reply_label)
        var remoteInput: RemoteInput =
            RemoteInput.Builder(context.resources.getString(R.string.notification_reply_key)).run {
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
                "메모 작성", replyPendingIntent
            )
                .addRemoteInput(remoteInput)
                .build()

        val mainIntent =
            Intent(context.applicationContext, MainActivity::class.java) // 알림 클릭 시 이동할 액티비티 지정
        val pendingIntent = PendingIntent.getActivity(
            context.applicationContext,
            0,
            mainIntent,
            PendingIntent.FLAG_MUTABLE
        )

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

        val repliedNotification = builder
            .build()

        // Issue the new notification.
        NotificationManagerCompat.from(context).apply {
            notify(
                context.resources.getInteger(R.integer.notification_id),
                repliedNotification
            )
        }
    }
}