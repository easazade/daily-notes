package ir.easazade.dailynotes.businesslogic.states

import ir.easazade.dailynotes.businesslogic.entities.User

typealias UState = UserRepositoryState

class UserRepositoryState private constructor(
  val inProgress: Boolean = false,
  val hasError: Boolean = false,
  val hasNoConnection: Boolean = false,
  val notLoggedIn: Boolean = false,
  val isSuccessful: Boolean = false,
  val isFailure: Boolean = false,
  val user: User? = null,
  val error: Throwable? = null,
  val failureReason: String? = null
) {

  companion object {
    //TODO the point of creating reasons and not states is because they are not of state material
    //TODO these are are just one state error or failure so we make reasons of failure
    //TODO note that when we make reasons we are not putting a simple part of our logic in our view
    //TODO the difference is only our view code and logic code will be a little shorter
    //TODO and we will need less observer on View side and less states to check for on logic side

    const val REASON_WRONG_EMAIL_FORMAT: String = "wrong email format"
    const val USER_NOT_FOUND: String = "user_not_found"
    const val REASON_EMAIL_EXISTS: String = "email exists"
    const val USERNAME_EXISTS: String = "username exists"
    const val REASON_WRONG_EMAIL_OR_PASSWORD: String = "wrong email or password"
    const val REASON_PASSWORD_WEAK: String = "password weak"
    const val REASON_NO_SUCH_EMAIL: String = "no such email"
    const val IMAGE_SIZE_TOO_LARGE: String = "image size too large"
    const val INVALID_INSTAGRAM_LINK: String = "invalid instagram link"
    const val WRONG_PASSWORD: String = "wrong password"
    const val PASSWORDS_DOES_NOT_MATCH: String = "passwords does not match"
    const val USERNAME_IS_EMPTY: String = "user username is empty"
    const val EMPTY_FAVORITE_LIST: String = "fav list is empty"
    const val NO_IMAGE_CHOSEN: String = "no image chosen"
    const val NO_NAME_TYPED: String = "no username typed"

    fun inProgress() = UState(inProgress = true)
    fun notLoggedIn() = UState(notLoggedIn = true)
    fun error(error: Throwable) = UState(hasError = true, error = error)
    fun noConnection() = UState(hasNoConnection = true)
    fun success(user: User) = UState(isSuccessful = true, user = user)
    fun failed(reason: String) = UState(isFailure = true, failureReason = reason)

  }

}