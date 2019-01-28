package ir.easazade.dailynotes.businesslogic.entities

data class User(
    val uuid: String,
    val username: String,
    val email: String,
    val notes: List<Note>
)