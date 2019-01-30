package ir.easazade.dailynotes.viewmodels.tasks

import com.jakewharton.rxrelay2.PublishRelay

class CommonTask {

  val notConnected = PublishRelay.create<Boolean>()
  val error = PublishRelay.create<Boolean>()
  val notLoggedIn = PublishRelay.create<Boolean>()
}