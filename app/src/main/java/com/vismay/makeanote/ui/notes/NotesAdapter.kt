package com.vismay.makeanote.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vismay.makeanote.R
import com.vismay.makeanote.data.local.db.entity.NoteEntity
import kotlinx.android.synthetic.main.notes_item_view.view.*

class NotesAdapter(private val notes: List<NoteEntity>) :
    RecyclerView.Adapter<NotesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder.from(parent)

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = notes[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = notes.size

    class ItemHolder private constructor(private var view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: NoteEntity) {
            view.text_title.text = item.title
            view.text_description.text = item.description
        }

        companion object {
            fun from(parent: ViewGroup): ItemHolder = ItemHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.notes_item_view, parent, false)
            )
        }
    }
}