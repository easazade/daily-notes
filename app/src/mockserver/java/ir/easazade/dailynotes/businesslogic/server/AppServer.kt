package ir.easazade.dailynotes.businesslogic.server

import io.reactivex.Observable
import ir.easazade.dailynotes.businesslogic.database.AppDatabase
import ir.easazade.dailynotes.businesslogic.database.DbUtils
import ir.easazade.dailynotes.businesslogic.database.RealmProvider
import ir.easazade.dailynotes.businesslogic.entities.DbNote
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.businesslogic.states.ServerState
import java.util.*
import java.util.concurrent.TimeUnit

class AppServer(private val provider: RealmProvider) : AppDatabase(provider), IAppServer {

    override fun signup(email: String, username: String, pass: String, passRepeat: String): Observable<ServerState> {
        val user = User(UUID.randomUUID().toString(), username, email, mutableListOf())
        saveUser(user)
        return Observable.just(ServerState.success(user)).delay(2000, TimeUnit.MILLISECONDS)
    }

    override fun sync(): Observable<ServerState> =
        Observable.just(ServerState.success(getUser()!!)).delay(2000, TimeUnit.MILLISECONDS)


    override fun login(email: String, pass: String): Observable<ServerState> {
        getUser()?.let { user ->
            return if (user.email == email)
                Observable.just(ServerState.success(user))
                    .delay(1000, TimeUnit.MILLISECONDS)
            else
                Observable.just(ServerState.failed(ServerState.WRONG_EMAIL_OR_PASSWORD))
                    .delay(1000, TimeUnit.MILLISECONDS)
        } ?: return Observable.just(ServerState.failed(ServerState.WRONG_EMAIL_OR_PASSWORD))
            .delay(1000, TimeUnit.MILLISECONDS)
    }

    override fun createNote(note: Note): Observable<ServerState> {
        saveUserNote(note)
        return Observable.just(ServerState.success(getUser()!!))
            .delay(1000, TimeUnit.MILLISECONDS)
    }

    override fun editNote(noteId: String, title: String?, content: String?, color: Int?): Observable<ServerState> {
        provider.get().where(DbNote::class.java).equalTo("uuid", noteId).findFirst()?.let { result ->
            var note = DbUtils.dbNoteToNote(result)
            title?.let { note = note.copy(title = it) }
            content?.let { note = note.copy(content = it) }
            color?.let { note = note.copy(color = it) }
            saveUserNote(note)
            return Observable.just(ServerState.success(getUser()!!))
                .delay(1000, TimeUnit.MILLISECONDS)
        } ?: return Observable.just(ServerState.failed("no such note"))
            .delay(1000, TimeUnit.MILLISECONDS)
    }

    override fun deleteNote(noteId: String): Observable<ServerState> {
        deleteUserNote(noteId)
        return Observable.just(ServerState.success(getUser()!!))
    }
}