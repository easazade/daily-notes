package ir.easazade.dailynotes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.sdk.navigation.Arguments
import ir.easazade.dailynotes.sdk.navigation.BaseFrag
import ir.easazade.dailynotes.sdk.navigation.ViewState

class NoteFrag : BaseFrag<NoteFrag.State, NoteFrag.Args>() {

  override fun getArgumentsAndSetProperties(args: Args) {}

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.frag_note, container, false)

  override fun initializeViews() {}

  override fun onLoadNewState(args: Args) {

  }

  override fun getCurrentState(): State = State(args)

  override fun observeViewModels() {

  }

  class Args : Arguments()
  class State(args: Args) : ViewState(args) {
    override fun createUpdate(): ViewState = this
  }
}