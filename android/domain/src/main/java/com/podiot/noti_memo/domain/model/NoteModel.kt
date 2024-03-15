package com.podiot.noti_memo.domain.model

import java.text.SimpleDateFormat
import java.util.Locale

data class NoteModel(
    val uid: Int? = null,
    val favorite: Boolean = false,
    val content: String?,
    val ymd: Int = SimpleDateFormat(
        "yyyyMMdd",
        Locale.getDefault()
    ).format(System.currentTimeMillis())
        .toInt(),
    val time: Long = System.currentTimeMillis()
)
