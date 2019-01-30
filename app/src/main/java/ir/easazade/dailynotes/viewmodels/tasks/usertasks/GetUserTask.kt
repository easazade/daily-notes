package ir.easazade.dailynotes.viewmodels.tasks.usertasks

import com.jakewharton.rxrelay2.PublishRelay
import ir.easazade.dailynotes.businesslogic.entities.User

class GetUserTask {
  val progress = PublishRelay.create<Boolean>()
  val success = PublishRelay.create<User>()
  val failed = PublishRelay.create<String>()
}