package ir.easazade.dailynotes.viewmodels.tasks

import com.jakewharton.rxrelay2.PublishRelay

object CommonTask {

  val notConnected = PublishRelay.create<Boolean>()
  val error = PublishRelay.create<Throwable>()
  val notLoggedIn = PublishRelay.create<Boolean>()
}