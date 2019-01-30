package ir.easazade.dailynotes.businesslogic.repos

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.server.IAppServer
import ir.easazade.dailynotes.businesslogic.states.NState

class NotesRepository(
  private val server: IAppServer,
  private val isConnected: () -> Boolean,
  private val isLoggedIn: () -> Boolean
) : INotesRepository {
  override fun createNote(note: Note): Observable<NState> {
    return Observable.create { emitter ->
      doOnLoggedIn(emitter) {
        server.createNote(note).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(NState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(NState.failed(state.failureReason!!))
            state.isSuccessful -> emitter.onNext(NState.success(state.note!!))
          }
        }
      }
    }

  }

  override fun editNote(
    noteId: String, title: String?, content: String?, color: Int?
  ): Observable<NState> {
    return Observable.create { emitter ->
      doOnLoggedIn(emitter) {
        server.editNote(noteId, title, content, color).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(NState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(NState.failed(state.failureReason!!))
            state.isSuccessful -> emitter.onNext(NState.success(state.note!!))
          }
        }
      }
    }
  }

  override fun deleteNote(noteId: String): Observable<NState> {
    return Observable.create { emitter ->
      doOnLoggedIn(emitter) {
        server.deleteNote(noteId).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(NState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(NState.failed(state.failureReason!!))
            state.isSuccessful -> emitter.onNext(NState.deleted())
          }
        }
      }
    }
  }

  private fun doOnConnectedAndLoggedIn(
    emitter: ObservableEmitter<NState>,
    onConnectedAndLoggedIn: () -> Unit
  ) {
    if (isConnected()) {
      if (isLoggedIn()) {
        emitter.onNext(NState.inProgress())
        onConnectedAndLoggedIn()
      } else {
        emitter.onNext(NState.notLoggedIn())
      }
    } else {
      emitter.onNext(NState.noConnection())
    }
  }

  private fun doOnLoggedIn(
    emitter: ObservableEmitter<NState>,
    onLoggedIn: () -> Unit
  ) {
    if (isLoggedIn()) {
      emitter.onNext(NState.inProgress())
      onLoggedIn()
    } else {
      emitter.onNext(NState.notLoggedIn())
    }
  }

  private fun doOnConnected(
    emitter: ObservableEmitter<NState>,
    onConnected: () -> Unit
  ) {
    if (isConnected()) {
      emitter.onNext(NState.inProgress())
      onConnected()
    } else {
      emitter.onNext(NState.noConnection())
    }
  }

}