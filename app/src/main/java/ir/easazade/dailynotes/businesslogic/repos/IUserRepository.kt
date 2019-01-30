package ir.easazade.dailynotes.businesslogic.repos

import io.reactivex.Observable
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.businesslogic.states.UState

interface IUserRepository {

  fun login(email: String, pass: String): Observable<UState>
  fun signup(email: String, username: String, pass: String, repeatPass: String): Observable<UState>
  fun sync(user: User): Observable<UState>
  fun isLoggedIn(): Boolean
  fun logout(): Observable<UState>
}