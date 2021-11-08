package com.vismay.makeanote.ui.shownotes.note

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.ui.base.BaseAdapter

class NoteAdapter(
    parentLifecycle: Lifecycle,
    notes: ArrayList<NoteEntity>
) : BaseAdapter<NoteEntity, NoteViewHolder>(parentLifecycle, notes) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }


}