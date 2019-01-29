package ir.easazade.dailynotes.businesslogic.server

import io.reactivex.Observable
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.states.ServerState

interface IAppServer {
    //user requests
    fun signup(): Observable<ServerState>

    fun sync(): Observable<ServerState>

    fun login(): Observable<ServerState>

    //note requests
    fun createNote(note: Note): Observable<ServerState>

    fun editNote(
        noteId: String,
        title: String,
        content: String,
        color: Int
    ): Observable<ServerState>

    fun deleteNote(noteId: String): Observable<ServerState>
}