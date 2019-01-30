package ir.easazade.dailynotes.businesslogic.server

import io.reactivex.Observable
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.businesslogic.states.ServerState

interface IAppServer {
    //user requests
    fun signup(email: String, username: String, pass: String, passRepeat: String): Observable<ServerState>

    fun sync(user:User): Observable<ServerState>

    fun login(email: String, pass: String): Observable<ServerState>

    //note requests
    fun createNote(note: Note): Observable<ServerState>

    fun editNote(
        noteId: String,
        title: String? = null,
        content: String? = null,
        color: Int? = null
    ): Observable<ServerState>

    fun deleteNote(noteId: String): Observable<ServerState>
}