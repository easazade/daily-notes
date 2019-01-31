package ir.easazade.dailynotes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.screens.adapters.HomeListAdapter
import ir.easazade.dailynotes.sdk.navigation.Arguments
import ir.easazade.dailynotes.sdk.navigation.BaseFrag
import ir.easazade.dailynotes.sdk.navigation.ViewState
import ir.easazade.dailynotes.utils.Roller
import kotlinx.android.synthetic.main.frag_main.mMainEmail
import kotlinx.android.synthetic.main.frag_main.mMainList
import kotlinx.android.synthetic.main.frag_main.mMainNotesCount
import kotlinx.android.synthetic.main.frag_main.mMainUserName

class HomeFrag : BaseFrag<HomeFrag.State, HomeFrag.Args>() {

  override fun getArgumentsAndSetProperties(args: Args) {}

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
  }

  override fun onLoadNewState(args: Args) {
    mActivity.userVm().getUser()?.let { user ->
      mMainEmail.visibility = View.VISIBLE
      mMainUserName.visibility = View.VISIBLE
      mMainNotesCount.visibility = View.VISIBLE
      mMainEmail.text = user.email
      mMainUserName.text = user.username
      mMainNotesCount.text = "notes : ${user.notes.size}"
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
      val adapter = HomeListAdapter(user.notes, roller) { note ->
        App.component().navigator().destination(NoteFrag()).withArguments(NoteFrag.Args(note)).go()
      }
      mMainList.layoutManager = GridLayoutManager(mActivity, 2)
      mMainList.adapter = adapter
    }
  }

  override fun getCurrentState(): State = State(args)

  override fun observeViewModels() {

  }

  class Args : Arguments()
  class State(args: Args) : ViewState(args) {
    override fun createUpdate(): ViewState = this
  }

}