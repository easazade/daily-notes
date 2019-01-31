package ir.easazade.dailynotes.businesslogic.entities

import java.sql.Timestamp

data class Note(
    val uuid: String,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val color: String

)