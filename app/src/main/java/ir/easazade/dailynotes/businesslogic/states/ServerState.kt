package ir.easazade.dailynotes.businesslogic.states

import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User

class ServerState private constructor(
    val hasError: Boolean = false,
    val hasItems: Boolean = false,
    val isSuccessful: Boolean = false,
    val isEmpty: Boolean = false,
    val failureReason: String? = null,
    val isFailed: Boolean = false,
    var errorMsg: Throwable? = null,
    var user: User? = null,
    var note: Note? = null,
    var token: String? = null,
    var tokenType: String? = null
) {

    init {
        user?.let { App.component().database().saveUser(it) }
        user?.let { App.component().dataBindings().updateUser(it) }
        note?.let { App.component().dataBindings().updateNote(it) }
    }

    companion object {
        const val WRONG_EMAIL_OR_PASSWORD = "wrong email or pass"
        const val ERROR_PARSING_JSON = "error parsing json"
        const val UNKNOWN_REASON = "unknown_reason"
        const val UNSUCCESSFUL_RESPONSE = "UNSUCCESSFUL_RESPONSE"
        const val WRONG_EMAIL = "WRONG_RMAIL"
        const val EMAIL_ALREADY_TAKEN = "email_already_taken"
        const val USERNAME_TAKEN = "username already taken"
        const val NOT_VALID = "not_valid"

        fun error(msg: Throwable): ServerState = ServerState(errorMsg = msg, hasError = true)

        fun auth(token: String, tokenType: String, user: User): ServerState =
            ServerState(token = token, tokenType = tokenType, user = user, isSuccessful = true)

        fun failed(reason: String): ServerState =
            ServerState(failureReason = reason, isFailed = true)

        fun success(note: Note): ServerState = ServerState(isSuccessful = true, note = note)

        fun success(): ServerState = ServerState(isSuccessful = true)

        fun success(user: User): ServerState = ServerState(user = user, isSuccessful = true)

    }
}