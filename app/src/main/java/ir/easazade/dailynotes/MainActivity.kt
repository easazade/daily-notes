package ir.easazade.dailynotes

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import ir.easazade.dailynotes.di.AndroidModule
import ir.easazade.dailynotes.di.AppComponent
import ir.easazade.dailynotes.di.DatabaseModule
import ir.easazade.dailynotes.di.ServerModule
import ir.easazade.dailynotes.screens.login.LoginFrag
import ir.easazade.dailynotes.screens.main.HomeFrag
import ir.easazade.dailynotes.sdk.BaseActivity
import ir.easazade.dailynotes.viewmodels.NotesViewModel
import ir.easazade.dailynotes.viewmodels.UserViewModel

class MainActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    //setting app component
    if (App.get(this).isAppComponentSet().not()) {
      App.get(this)
          .setAppComponent(
              AppComponent(
                  App.get(this),
                  DatabaseModule(),
                  ServerModule(),
                  AndroidModule(this)
              )
          )
    }
    //setting viewModels
    if (userViewModel == null)
      userViewModel = ViewModelProviders.of(this, App.component().viewModelFactory())
          .get(UserViewModel::class.java)
    if (notesViewModel == null)
      notesViewModel = ViewModelProviders.of(this, App.component().viewModelFactory())
          .get(NotesViewModel::class.java)

    //startig ui
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    if (userVm().isLoggedIn()) {
      App.component()
          .navigator()
          .destination(HomeFrag())
          .withArguments(HomeFrag.Args())
          .go()
    } else {
      App.component()
          .navigator()
          .destination(LoginFrag())
          .withArguments(LoginFrag.Args())
          .go()
    }

  }
}
