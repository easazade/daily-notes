package ir.easazade.dailynotes.screens.login

import ir.easazade.dailynotes.sdk.navigation.Arguments
import ir.easazade.dailynotes.sdk.navigation.BaseFrag
import ir.easazade.dailynotes.sdk.navigation.ViewState

class LoginFrag : BaseFrag<LoginFrag.State, LoginFrag.Args>() {

  override fun getArgumentsAndSetProperties(args: Args) {}

  override fun initializeViews() {}

  override fun onLoadNewState(args: Args) {

  }

  override fun getCurrentState(): State = State(args)

  override fun observeViewModels() {

  }

  class Args : Arguments()
  class State(args: Arguments) : ViewState(args) {
    override fun createUpdate(): ViewState = this
  }

}