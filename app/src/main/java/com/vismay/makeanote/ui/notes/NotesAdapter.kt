package com.vismay.makeanote.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import com.vismay.makeanote.databinding.NotesItemViewBinding
import com.vismay.makeanote.utils.Constants
import com.vismay.makeanote.utils.extensions.DateExtensions.getFormattedDate

class NotesAdapter(
    private val notes: MutableList<NoteEntity>,
    private val onItemClick: (Triple<NoteEntity, Boolean, Int>) -> Unit
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

    fun updateAdapter(notes: List<NoteEntity>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    fun addNote(note: NoteEntity, position: Int) {
        notes.add(note)
        if (position == -1) {
            notifyItemInserted(notes.lastIndex + 1)
        } else {
            notifyItemInserted(position)
        }
    }

    fun deleteNote(note: NoteEntity, position: Int) {
        notes.remove(note)
        notifyItemRemoved(position)
    }

    class NoteItemHolder(private var binding: NotesItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NoteEntity, onItemClick: (Triple<NoteEntity, Boolean, Int>) -> Unit) {
            var title = ""
            var description = ""
            /*Put this logic inside a helper/extension function.*/
            item.note?.run {
                val indexOfSpecialChar = indexOf(Constants.SPECIAL_CHAR, 0)
                if (indexOfSpecialChar == -1) {
                    if (length > 65) {
                        title = substring(0, 65)
                        description = substring(65, length)
                    } else {
                        title = this
                    }
                } else {
                    title = substring(0, indexOfSpecialChar)
                    description =
                        substring(indexOfSpecialChar, length).replace(Constants.SPECIAL_CHAR, "")
                }
            }
            binding.run {
                constraintLayoutNoteRoot.setOnClickListener {
                    onItemClick(Triple(item, false, layoutPosition))
                }
                constraintLayoutNoteRoot.setOnLongClickListener {
                    onItemClick(Triple(item, true, layoutPosition))
                    return@setOnLongClickListener true
                }
                textTitle.text = title
                textDescription.text = description
                textDateTime.text = item.date?.getFormattedDate()
            }
        }
    }
}