package ir.easazade.dailynotes.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.repos.INotesRepository
import ir.easazade.dailynotes.businesslogic.states.NState
import ir.easazade.dailynotes.viewmodels.tasks.CommonTask
import ir.easazade.dailynotes.viewmodels.tasks.notetasks.CreateNoteTask
import ir.easazade.dailynotes.viewmodels.tasks.notetasks.DeleteNoteTask
import ir.easazade.dailynotes.viewmodels.tasks.notetasks.EditNoteTask

class NotesViewModel(
  private val notesRepo: INotesRepository,
  private val io: Scheduler,
  private val ui: Scheduler
) : ViewModel() {

  private val disposables = CompositeDisposable()

  val createNoteTask = CreateNoteTask()
  val editNoteTask = EditNoteTask()
  val deleteNoteTask = DeleteNoteTask()

  fun createNote(note: Note) {
    disposables.add(notesRepo.createNote(note)
        .subscribeOn(io).observeOn(ui)
        .subscribe { state ->
          if (state.inProgress.not()) createNoteTask.progress.accept(false)
          handleCommonTask(state)
          when {
            state.inProgress -> createNoteTask.progress.accept(true)
            state.isFailure -> state.failureReason?.let { createNoteTask.failed.accept(it) }
            state.isSuccessful -> {
              state.note?.let { createNoteTask.success.accept(it) }
            }
          }
        }
    )
  }

  fun editNote(
    noteId: String,
    title: String? = null,
    content: String? = null,
    color: String? = null
  ) {
    disposables.add(notesRepo.editNote(noteId, title, content, color)
        .subscribeOn(io).observeOn(ui)
        .subscribe { state ->
          if (state.inProgress.not()) editNoteTask.progress.accept(false)
          handleCommonTask(state)
          when {
            state.inProgress -> editNoteTask.progress.accept(true)
            state.isFailure -> state.failureReason?.let { editNoteTask.failed.accept(it) }
            state.isSuccessful -> {
              state.note?.let { editNoteTask.success.accept(it) }
            }
          }
        }
    )
  }

  fun deleteNote(noteId: String) {
    disposables.add(notesRepo.deleteNote(noteId)
        .subscribeOn(io).observeOn(ui)
        .subscribe { state ->
          if (state.inProgress.not()) deleteNoteTask.progress.accept(false)
          handleCommonTask(state)
          when {
            state.inProgress -> deleteNoteTask.progress.accept(true)
            state.isFailure -> state.failureReason?.let { deleteNoteTask.failed.accept(it) }
            state.isSuccessful -> state.deletedNotId?.let { deleteNoteTask.success.accept(it) }
          }
        }
    )
  }

  private fun handleCommonTask(state: NState) {
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
    super.onCleared()
  }

}