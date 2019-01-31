package ir.easazade.dailynotes.businesslogic.repos

import io.reactivex.Observable
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.states.NState

interface INotesRepository {

  fun createNote(note: Note): Observable<NState>
  fun editNote(noteId: String, title: String? = null, content: String? = null, color: String? = null)
      : Observable<NState>

  fun deleteNote(noteId: String): Observable<NState>

}