package ir.easazade.dailynotes.viewmodels.tasks.usertasks

import com.jakewharton.rxrelay2.PublishRelay
import ir.easazade.dailynotes.businesslogic.entities.User

class LoginTask {

  val progress = PublishRelay.create<Boolean>()
  val loggedIn = PublishRelay.create<User>()
  val failed = PublishRelay.create<String>()

}