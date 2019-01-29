package ir.easazade.dailynotes.businesslogic.database

import io.realm.RealmList
import ir.easazade.dailynotes.businesslogic.entities.DbNote
import ir.easazade.dailynotes.businesslogic.entities.DbUser
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User
import java.sql.Timestamp

class DbUtils {

    companion object {
        fun dbUserToUser(dbUser: DbUser): User = User(
            dbUser.uuid,
            dbUser.username,
            dbUser.email,
            dbUser.notes.map { dbNoteToNote(it) }
        )

        fun dbNoteToNote(dbNote: DbNote): Note = Note(
            dbNote.uuid,
            dbNote.userId,
            dbNote.title,
            dbNote.content,
            Timestamp(dbNote.createdAt.time),
            dbNote.color
        )

        fun noteToDbNote(note: Note): DbNote = DbNote(
            note.uuid,
            note.userId,
            note.title,
            note.content,
            note.createdAt,
            note.color
        )

        fun userToDbUser(user: User): DbUser = DbUser(
            user.uuid,
            user.username,
            user.email,
            user.notes.map { noteToDbNote(it) }.let { RealmList<DbNote>().apply { addAll(it) } }
        )

    }

}