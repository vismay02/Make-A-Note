package com.vismay.makeanote.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.utils.Constants
import kotlinx.android.synthetic.main.notes_item_view.view.*

class NotesAdapter(
    private val notes: List<NoteEntity>,
    private val onItemClick: (Pair<NoteEntity, Boolean>) -> Unit
) :
    RecyclerView.Adapter<NotesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder.from(parent)

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = notes[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount(): Int = notes.size

    class ItemHolder private constructor(private var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: NoteEntity, onItemClick: (Pair<NoteEntity, Boolean>) -> Unit) {
            view.setOnClickListener {
                onItemClick(Pair(item, false))
            }
            view.setOnLongClickListener {
                onItemClick(Pair(item, true))
                return@setOnLongClickListener true
            }
            var title = ""
            var description = ""
            item.note?.run {
                val indexOfNewLine = indexOf(Constants.SPECIAL_CHAR, 0)
                if (indexOfNewLine == -1) {
                    if (length > 65) {
                        title = substring(0, 65)
                        description = substring(65, length)
                    } else {
                        title = this
                    }
                } else {
                    title = substring(0, indexOfNewLine).replace(Constants.SPECIAL_CHAR, "\n")
                    description =
                        substring(indexOfNewLine, length).replace(Constants.SPECIAL_CHAR, "")
                }
            }
            view.text_title.text = title
            view.text_description.text = description
        }

        companion object {
            fun from(parent: ViewGroup): ItemHolder = ItemHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.notes_item_view, parent, false)
            )
        }
    }
}