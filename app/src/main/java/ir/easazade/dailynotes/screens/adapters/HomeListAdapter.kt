package ir.easazade.dailynotes.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.utils.DateUtils

class HomeListAdapter(
  private val mItems: MutableList<Note>
) : RecyclerView.Adapter<HomeListAdapter.NoteVH>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
    return NoteVH(
        LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
  }

  override fun getItemCount(): Int = mItems.size

  override fun onBindViewHolder(holder: NoteVH, position: Int) {
    val note = mItems[position]
    holder.title.text = note.title
    holder.content.text = note.content
    holder.date.text = DateUtils.formatDate(note.createdAt)
  }

  class NoteVH(root: View) : RecyclerView.ViewHolder(root) {
    val title = root.findViewById<TextView>(R.id.mNoteItemTitle)
    val content = root.findViewById<TextView>(R.id.mNoteItemContent)
    val date = root.findViewById<TextView>(R.id.mNoteItemDate)
  }

}