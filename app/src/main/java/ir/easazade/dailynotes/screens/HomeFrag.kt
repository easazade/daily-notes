package ir.easazade.dailynotes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.screens.adapters.HomeListAdapter
import ir.easazade.dailynotes.screens.listeners.AppBarStateChangeListener
import ir.easazade.dailynotes.screens.viewobjects.AddNoteDialog
import ir.easazade.dailynotes.sdk.navigation.Arguments
import ir.easazade.dailynotes.sdk.navigation.BaseFrag
import ir.easazade.dailynotes.sdk.navigation.ViewState
import ir.easazade.dailynotes.utils.Roller
import kotlinx.android.synthetic.main.frag_main.*

class HomeFrag : BaseFrag<HomeFrag.State, HomeFrag.Args>() {

  private var mAdapter: HomeListAdapter? = null

  override fun getArgumentsAndSetProperties(args: Args) {}

  val roller = Roller(
      R.color.note_color_1,
      R.color.note_color_2,
      R.color.note_color_3,
      R.color.note_color_4,
      R.color.note_color_5,
      R.color.note_color_6,
      R.color.note_color_7,
      R.color.note_color_8
  )

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? =
    inflater.inflate(R.layout.frag_main, container, false)

  override fun initializeViews() {
    mMainEmail.visibility = View.INVISIBLE
    mMainUserName.visibility = View.INVISIBLE
    mMainNotesCount.visibility = View.INVISIBLE
    mMainAppBar.addOnOffsetChangedListener(AppBarStateChangeListener { appBarLayout, state ->
      when (state) {
        AppBarStateChangeListener.COLLAPSED -> mMainAddBtn.hide()
        AppBarStateChangeListener.EXPANDED -> mMainAddBtn.show()
        AppBarStateChangeListener.IDLE -> mMainAddBtn.show()
      }
    })
    mMainAddBtn.setOnClickListener { _ ->
      mActivity.findViewById<AddNoteDialog>(R.id.mActivityAddNoteDialog)
          .show()
    }
  }

  override fun onLoadNewState(args: Args) {
    mActivity.userVm()
        .getUser()
        ?.let { user ->
          mMainEmail.visibility = View.VISIBLE
          mMainUserName.visibility = View.VISIBLE
          mMainNotesCount.visibility = View.VISIBLE
          mMainEmail.text = user.email
          mMainUserName.text = user.username
          mMainNotesCount.text = "notes : ${user.notes.size}"
          mAdapter = HomeListAdapter(user.notes) { note ->
            App.component()
                .navigator()
                .destination(NoteFrag())
                .withArguments(NoteFrag.Args(note))
                .go()
          }
          mMainList.layoutManager = GridLayoutManager(mActivity, 2)
          mMainList.adapter = mAdapter
        }
  }

  override fun getCurrentState(): State = State(args)

  override fun observeViewModels() {
    fragSubscriptions.addAll(
        mActivity.notesVm().deleteNoteTask.success.observeOn(ui).subscribe {
          mAdapter?.deleteNote(it)
        },
        mActivity.notesVm().createNoteTask.success.observeOn(ui).subscribe {
          mAdapter?.addNote(it)
        }
    )
  }

  class Args : Arguments()
  class State(args: Args) : ViewState(args) {
    override fun createUpdate(): ViewState = this
  }

}