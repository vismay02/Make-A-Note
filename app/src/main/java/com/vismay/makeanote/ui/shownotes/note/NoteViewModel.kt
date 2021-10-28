package com.vismay.makeanote.ui.shownotes.note

import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.ui.base.BaseItemViewModel
import javax.inject.Inject

class NoteViewModel @Inject constructor(): BaseItemViewModel<NoteEntity>() {

    override fun onCreate() {
    }
}