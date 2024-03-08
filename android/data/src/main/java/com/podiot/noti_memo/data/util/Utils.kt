package com.podiot.noti_memo.data.util

import com.podiot.noti_memo.data.model.Note
import com.podiot.noti_memo.domain.model.NoteModel


fun NoteModel.toNote(): Note =
    Note(content = this.content, favorite = this.favorite, ymd = this.ymd, time = this.time)