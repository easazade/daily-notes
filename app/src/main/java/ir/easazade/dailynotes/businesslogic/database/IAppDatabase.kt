package ir.easazade.dailynotes.businesslogic.database

import ir.easazade.dailynotes.businesslogic.entities.AboutUs
import ir.easazade.dailynotes.businesslogic.entities.AuthInfo
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User

interface IAppDatabase {

    fun getUser(): User
    fun saveAuthInfo(authInfo: AuthInfo)
    fun getAuthInfo(): AuthInfo
    fun saveAboutUs(aboutUs: AboutUs)
    fun getAboutUs(): AboutUs
    fun getUserNote(id: String): Note
    fun saveUserNote(note: Note)
    fun deleteUserNote(id: String)

}