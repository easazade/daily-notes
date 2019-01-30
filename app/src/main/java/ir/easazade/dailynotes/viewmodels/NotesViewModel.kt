package ir.easazade.dailynotes.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import ir.easazade.dailynotes.businesslogic.repos.INotesRepository

class NotesViewModel(
  private val notesRepo: INotesRepository,
  private val io: Scheduler,
  private val ui: Scheduler
) : ViewModel() {



}