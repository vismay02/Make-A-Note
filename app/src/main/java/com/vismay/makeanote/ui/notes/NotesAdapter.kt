package com.vismay.makeanote.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.NotesItemViewBinding
import com.vismay.makeanote.utils.Constants

class NotesAdapter(
    private val notes: List<NoteEntity>,
    private val onItemClick: (Pair<NoteEntity, Boolean>) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemHolder =
        NoteItemHolder(
            NotesItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NoteItemHolder, position: Int) {
        val item = notes[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount(): Int = notes.size

    class NoteItemHolder(private var binding: NotesItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteEntity, onItemClick: (Pair<NoteEntity, Boolean>) -> Unit) {
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
            binding.run {
                constraintLayoutNoteRoot.setOnClickListener {
                    onItemClick(Pair(item, false))
                }
                constraintLayoutNoteRoot.setOnLongClickListener {
                    onItemClick(Pair(item, true))
                    return@setOnLongClickListener true
                }
                textTitle.text = title
                textDescription.text = description
            }
        }
    }
}