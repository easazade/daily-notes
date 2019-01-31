package ir.easazade.dailynotes.screens

import android.graphics.Color
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.screens.viewobjects.AddNoteDialog
import ir.easazade.dailynotes.sdk.navigation.Arguments
import ir.easazade.dailynotes.sdk.navigation.BaseFrag
import ir.easazade.dailynotes.sdk.navigation.ViewState
import kotlinx.android.synthetic.main.frag_note.mNoteContent
import kotlinx.android.synthetic.main.frag_note.mNoteDate
import kotlinx.android.synthetic.main.frag_note.mNoteDelete
import kotlinx.android.synthetic.main.frag_note.mNoteEdit
import kotlinx.android.synthetic.main.frag_note.mNoteTitle
import kotlinx.android.synthetic.main.frag_note.mNoteToolbar

class NoteFrag : BaseFrag<NoteFrag.State, NoteFrag.Args>() {

  private val mDeleteDialog: AlertDialog by lazy {
    AlertDialog.Builder(mActivity)
        .setMessage(R.string.confirm_delete_msg)
        .setPositiveButton(R.string.yes) { dialog, which ->
          mActivity.notesVm().deleteNote(args.note.uuid)
          dialog.dismiss()
          App.component().navigator().destination(HomeFrag()).withArguments(HomeFrag.Args()).go()
        }
        .setNegativeButton(R.string.no) { dialog, which ->
          dialog.dismiss()
        }
        .create()
  }

  override fun getArgumentsAndSetProperties(args: Args) {}

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.frag_note, container, false)

  override fun initializeViews() {}

  override fun onLoadNewState(args: Args) {
    mNoteToolbar.setBackgroundColor(Color.parseColor(args.note.color))
    mNoteTitle.text = args.note.title
    mNoteContent.text = args.note.content
    mNoteDate.text = DateUtils.formatDateTime(mActivity, args.note.createdAt.time, DateUtils.FORMAT_ABBREV_MONTH)
    mNoteDelete.setOnClickListener {
      mDeleteDialog.show()
    }
    mNoteEdit.setOnClickListener {
      mActivity.findViewById<AddNoteDialog>(R.id.mActivityAddNoteDialog).apply {
        setModeEdit(args.note.uuid, args.note.title, args.note.content)
      }.show()
    }
  }

  override fun getCurrentState(): State = State(args)

  override fun observeViewModels() {
    fragSubscriptions.addAll(
        mActivity.notesVm().editNoteTask.success.observeOn(ui).subscribe { note ->
          this.args = Args(note)
          onLoadNewState(args)
        }
    )
  }

  class Args(val note: Note) : Arguments()
  class State(args: Args) : ViewState(args) {
    override fun createUpdate(): ViewState = this
  }
}