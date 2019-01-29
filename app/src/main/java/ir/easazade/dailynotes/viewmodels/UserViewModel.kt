package ir.easazade.dailynotes.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository

class UserViewModel(
  private val userRepo: IUserRepository,
  private val io: Scheduler,
  private val ui: Scheduler
) : ViewModel() {

  fun isLoggedIn(): Boolean = false

}