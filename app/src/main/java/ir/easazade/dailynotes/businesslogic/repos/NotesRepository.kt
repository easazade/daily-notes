package ir.easazade.dailynotes.businesslogic.repos

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import ir.easazade.dailynotes.businesslogic.database.IAppDatabase
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.server.IAppServer
import ir.easazade.dailynotes.businesslogic.states.NState

class NotesRepository(
  private val database: IAppDatabase,
  private val server: IAppServer,
  private val isConnected: () -> Boolean,
  private val isLoggedIn: () -> Boolean
) : INotesRepository {

  override fun createNote(note: Note): Observable<NState> {
    return Observable.create { emitter ->
      database.saveUserNote(note)
      emitter.onNext(NState.success(note))
      doOnConnectedAndLoggedIn(emitter) {
        server.createNote(note).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(NState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(NState.failed(state.failureReason!!))
          }
        }
      }
    }

  }

  override fun editNote(noteId: String, title: String?, content: String?, color: String?): Observable<NState> {
    return Observable.create { emitter ->
      var note = database.getUserNote(noteId)
      if (note != null) {
        title?.let { note = note!!.copy(title = it) }
        content?.let { note = note!!.copy(content = it) }
        color?.let { note = note!!.copy(color = it) }
        database.saveUserNote(note!!)
        emitter.onNext(NState.success(note!!))
      }
      doOnConnectedAndLoggedIn(emitter) {
        server.editNote(noteId, title, content, color).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(NState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(NState.failed(state.failureReason!!))
          }
        }
      }
    }
  }

  override fun deleteNote(noteId: String): Observable<NState> {
    return Observable.create { emitter ->
      database.deleteUserNote(noteId)
      emitter.onNext(NState.deleted(noteId))
      doOnConnectedAndLoggedIn(emitter) {
        server.deleteNote(noteId).subscribe { state ->
          when {
            state.hasError -> emitter.onNext(NState.error(state.errorMsg!!))
            state.isFailed -> emitter.onNext(NState.failed(state.failureReason!!))
          }
        }
      }
    }
  }

  //####################################### private methods #########################################
  private fun doOnConnectedAndLoggedIn(
    emitter: ObservableEmitter<NState>,
    onConnectedAndLoggedIn: () -> Unit
  ) {
    if (isConnected().and(isLoggedIn())) {
      emitter.onNext(NState.inProgress())
      onConnectedAndLoggedIn()
    }
  }

  private fun doOnLoggedIn(
    emitter: ObservableEmitter<NState>,
    onLoggedIn: () -> Unit
  ) {
    if (isLoggedIn()) {
      emitter.onNext(NState.inProgress())
      onLoggedIn()
    }
  }

  private fun doOnConnected(
    emitter: ObservableEmitter<NState>,
    onConnected: () -> Unit
  ) {
    if (isConnected()) {
      emitter.onNext(NState.inProgress())
      onConnected()
    }
  }

}