package ir.easazade.dailynotes.viewmodels.tasks.usertasks

import com.jakewharton.rxrelay2.PublishRelay
import ir.easazade.dailynotes.businesslogic.entities.User

class SyncTask {
  val progress = PublishRelay.create<Boolean>()
  val syncFinished = PublishRelay.create<User>()
  val failed = PublishRelay.create<Boolean>()
}