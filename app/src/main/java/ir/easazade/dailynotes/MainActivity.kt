package ir.easazade.dailynotes

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import ir.easazade.dailynotes.businesslogic.ColorRoller
import ir.easazade.dailynotes.di.AndroidModule
import ir.easazade.dailynotes.di.AppComponent
import ir.easazade.dailynotes.di.DatabaseModule
import ir.easazade.dailynotes.di.ServerModule
import ir.easazade.dailynotes.screens.HomeFrag
import ir.easazade.dailynotes.screens.LoginFrag
import ir.easazade.dailynotes.screens.viewobjects.AddNoteDialog
import ir.easazade.dailynotes.sdk.BaseActivity
import ir.easazade.dailynotes.viewmodels.NotesViewModel
import ir.easazade.dailynotes.viewmodels.UserViewModel
import ir.easazade.dailynotes.viewmodels.tasks.CommonTask

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
    //setting ColorRoller
    App.component().database().getUser()?.let { user ->
      ColorRoller.set(user.notes.last())
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

    //observing CommonTask
    subscriptions.addAll(
        CommonTask.notLoggedIn.observeOn(AndroidSchedulers.mainThread()).subscribe { notLoggedIn ->
          if (notLoggedIn)
            App.component().navigator().destination(LoginFrag()).withArguments(
                LoginFrag.Args()
            ).go()
        },
        CommonTask.notConnected.observeOn(AndroidSchedulers.mainThread()).subscribe {
          showNoInternetConnectionSnackBar()
        }
    )

  }

  override fun onBackPressed() {
    if (findViewById<AddNoteDialog>(
            R.id.mActivityAddNoteDialog).mState == AddNoteDialog.State.VISIBLE) {
      findViewById<AddNoteDialog>(R.id.mActivityAddNoteDialog).hide()
    } else
      App.component().navigator().back()
  }

}
