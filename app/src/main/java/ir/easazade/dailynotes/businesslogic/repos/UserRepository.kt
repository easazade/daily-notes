package ir.easazade.dailynotes.businesslogic.repos

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import ir.easazade.dailynotes.businesslogic.database.IAppDatabase
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.businesslogic.server.IAppServer
import ir.easazade.dailynotes.businesslogic.states.UState

class UserRepository(
  private val server: IAppServer,
  private val isConnected: () -> Boolean,
  private val isLoggedIn: () -> Boolean,
  private val database: IAppDatabase
) : IUserRepository {


  override fun login(email: String, pass: String): Observable<UState> {
    return Observable.create { emitter ->
      doOnConnected(emitter) {
        server.login(email, pass).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(UState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(UState.failed(state.failureReason!!))
            state.isSuccessful -> {
              emitter.onNext(UState.success(state.user!!))
              database.saveAuthInfo(state.authInfo!!)
            }
          }
        }
      }
    }
  }

  override fun signup(
    email: String, username: String, pass: String, repeatPass: String
  ): Observable<UState> {
    return Observable.create { emitter ->
      doOnConnected(emitter) {
        server.signup(email, username, pass, repeatPass)
            .subscribe { state ->
              when {
                state.hasError -> emitter.onNext(UState.error(state.errorMsg!!))
                state.isFailed -> emitter.onNext(UState.failed(state.failureReason!!))
                state.isSuccessful -> emitter.onNext(UState.success(state.user!!))
              }
            }
      }
    }
  }

  override fun sync(): Observable<UState> {
    return Observable.create { emitter ->
      doOnConnectedAndLoggedIn(emitter) {
        server.sync(database.getUser()!!).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(UState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(UState.failed(state.failureReason!!))
            state.isSuccessful -> emitter.onNext(UState.success(state.user!!))
          }
        }
      }
    }
  }

  override fun logout(): Observable<UState> {
    database.deleteUser()
    return Observable.just(UState.notLoggedIn())
  }

  override fun isLoggedIn(): Boolean = database.isLoggedIn()

  //####################################### helper methods ##################################
  private fun doOnConnectedAndLoggedIn(
    emitter: ObservableEmitter<UState>,
    onConnectedAndLoggedIn: () -> Unit
  ) {
    if (isConnected()) {
      if (isLoggedIn()) {
        emitter.onNext(UState.inProgress())
        onConnectedAndLoggedIn()
      } else {
        emitter.onNext(UState.notLoggedIn())
      }
    } else {
      emitter.onNext(UState.noConnection())
    }
  }

  private fun doOnLoggedIn(
    emitter: ObservableEmitter<UState>,
    onLoggedIn: () -> Unit
  ) {
    if (isLoggedIn()) {
      emitter.onNext(UState.inProgress())
      onLoggedIn()
    } else {
      emitter.onNext(UState.notLoggedIn())
    }
  }

  private fun doOnConnected(
    emitter: ObservableEmitter<UState>,
    onConnected: () -> Unit
  ) {
    if (isConnected()) {
      emitter.onNext(UState.inProgress())
      onConnected()
    } else {
      emitter.onNext(UState.noConnection())
    }
  }

}