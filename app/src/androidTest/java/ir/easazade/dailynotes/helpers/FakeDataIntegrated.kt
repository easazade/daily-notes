package ir.easazade.dailynotes.helpers

import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.utils.DateUtils
import java.util.*

class FakeDataIntegrated {

    companion object {
        fun fakeUsers(count: Int): MutableList<User> {
            val users = mutableListOf<User>()
            for (i in 0 until count) {
                users.add(
                    User(
                        UUID.randomUUID().toString(),
                        "shapoor$i",
                        "email$i@gmail.com",
                        fakeNotes(5)
                    )
                )
            }
            return users
        }

        fun fakeNotes(count: Int): MutableList<Note> {
            val notes = mutableListOf<Note>()
            for (i in 0 until count) {
                notes.add(
                    Note(
                        UUID.randomUUID().toString(),
                        "shapoor$i",
                        "email$i@gmail.com",
                        "some content",
                        DateUtils.currentTime(),
                        0xff54cd.toInt()
                    )
                )
            }
            return notes
        }

    }

}