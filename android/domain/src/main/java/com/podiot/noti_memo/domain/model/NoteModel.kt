package com.podiot.noti_memo.domain.model

data class NoteModel(
    val uid: Int? = null,
    val favorite: Boolean = false,
    val content: String?,
    val ymd: Int = 0,
    val time: Long = 0
)
