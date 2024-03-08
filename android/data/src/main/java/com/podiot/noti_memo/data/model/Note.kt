package com.podiot.noti_memo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.podiot.noti_memo.domain.model.NoteModel

@Entity
data class Note(
    @PrimaryKey
    val uid: Int?= null,
    @ColumnInfo(name = "image_b")
    val image_b: Boolean = false,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false,
    @ColumnInfo(name = "content")
    val content: String?,
    @ColumnInfo(name = "ymd")
    val ymd: Int = 0,
    @ColumnInfo(name = "time")
    val time: Long = 0,
    @ColumnInfo(name = "image")
    var image: String? = null,
    @ColumnInfo(name = "LATITUDE")
    val LATITUDE: Double? = null,
    @ColumnInfo(name = "LONGITUDE")
    val LONGITUDE: Double? = null
) {
    fun toNoteModel(): NoteModel = NoteModel(uid, favorite, content, ymd, time)
}
