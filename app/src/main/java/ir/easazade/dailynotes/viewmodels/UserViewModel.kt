package ir.easazade.dailynotes.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ir.easazade.dailynotes.businesslogic.repos.IUserRepository
import ir.easazade.dailynotes.businesslogic.states.UState
import ir.easazade.dailynotes.viewmodels.tasks.CommonTask
import ir.easazade.dailynotes.viewmodels.tasks.usertasks.LoginTask
import ir.easazade.dailynotes.viewmodels.tasks.usertasks.SignupTask
import ir.easazade.dailynotes.viewmodels.tasks.usertasks.SyncTask

class UserViewModel(
  private val userRepo: IUserRepository,
  private val io: Scheduler,
  private val ui: Scheduler
) : ViewModel() {

  val loginDisposables = CompositeDisposable()
  val signupDisposables = CompositeDisposable()
  val disposables = CompositeDisposable()

  val loginTask = LoginTask()
  val signupTask = SignupTask()
  val syncTask = SyncTask()

  fun isLoggedIn(): Boolean = userRepo.isLoggedIn()

  fun login(email: String, pass: String) {
    loginDisposables.add(userRepo.login(email, pass)
        .subscribeOn(io).observeOn(ui)
        .subscribe { state ->
          if (state.inProgress.not()) loginTask.progress.accept(false)
          handleCommonTask(state)
          when {
            state.inProgress -> loginTask.progress.accept(true)
            state.isFailure -> loginTask.failed.accept(true)
            state.isSuccessful -> {
              state.user?.let { loginTask.loggedIn.accept(it) }
            }
          }
        }
    )
  }

  fun signup(email: String, username: String, pass: String, passRepeat: String) {
    signupDisposables.add(userRepo.signup(email, username, pass, passRepeat)
        .subscribeOn(io).observeOn(ui)
        .subscribe { state ->
          if (state.inProgress.not()) signupTask.progress.accept(false)
          handleCommonTask(state)
          when {
            state.inProgress -> signupTask.progress.accept(true)
            state.isFailure -> signupTask.failed.accept(true)
            state.isSuccessful -> {
              state.user?.let { signupTask.signedUp.accept(it) }
            }
          }
        }
    )
  }

  fun sync() {
    disposables.add(userRepo.sync()
        .subscribeOn(io).observeOn(ui)
        .subscribe { state ->
          if (state.inProgress.not()) syncTask.progress.accept(false)
          handleCommonTask(state)
          when {
            state.inProgress -> syncTask.progress.accept(true)
            state.isFailure -> syncTask.failed.accept(true)
            state.isSuccessful -> {
              state.user?.let { syncTask.syncFinished.accept(it) }
            }
          }
        }
    )
  }

  fun logout() {
    disposables.add(userRepo.logout()
        .subscribeOn(io).observeOn(ui)
        .subscribe { handleCommonTask(it) }
    )
  }

  //################################ private methods #########################################3

  private fun handleCommonTask(state: UState) {
    when {
      state.hasError -> {
        CommonTask.error.accept(state.error)
      }
      state.hasNoConnection -> {
        CommonTask.notConnected.accept(true)
      }
      state.notLoggedIn -> {
        CommonTask.notLoggedIn.accept(true)
      }
    }
  }

  override fun onCleared() {
    disposables.clear()
    signupDisposables.clear()
    loginDisposables.clear()
    super.onCleared()
  }

}