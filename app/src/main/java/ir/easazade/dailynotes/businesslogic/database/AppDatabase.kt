package ir.easazade.dailynotes.businesslogic.database

import io.realm.Realm
import ir.easazade.dailynotes.businesslogic.entities.AboutUs
import ir.easazade.dailynotes.businesslogic.entities.AuthInfo
import ir.easazade.dailynotes.businesslogic.entities.DbNote
import ir.easazade.dailynotes.businesslogic.entities.DbUser
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.utils.ListUtils

open class AppDatabase(private val provider: RealmProvider) : IAppDatabase {

  companion object {
    fun createInstance(provider: RealmProvider): AppDatabase = AppDatabase(provider)
  }

  override fun getUser(): User? {
    provider.get().where(DbUser::class.java).findFirst()?.let {
      return DbUtils.dbUserToUser(it)
    } ?: return null

  }

  override fun saveUser(user: User) {
    val currentUser = getUser()
    if (currentUser != null) {
      if (currentUser.uuid == user.uuid)
        transaction { it.copyToRealmOrUpdate(DbUtils.userToDbUser(user)) }
      else
        transaction {
          it.where(DbUser::class.java).findFirst()?.deleteFromRealm()
          it.copyToRealmOrUpdate(DbUtils.userToDbUser(user))
        }
    } else {
      transaction { it.copyToRealmOrUpdate(DbUtils.userToDbUser(user)) }
    }
  }

  override fun deleteUser() {
    transaction { it.where(DbUser::class.java).findAll().deleteAllFromRealm() }
  }

  override fun isLoggedIn(): Boolean = getUser() != null

  override fun saveAuthInfo(authInfo: AuthInfo) {
    transaction { it.copyToRealmOrUpdate(authInfo) }
  }

  override fun getAuthInfo(): AuthInfo? = provider.get().where(AuthInfo::class.java).findFirst()

  override fun saveAboutUs(aboutUs: AboutUs) {
    transaction { it.copyToRealmOrUpdate(aboutUs) }
  }

  override fun getAboutUs(): AboutUs? = provider.get().where(AboutUs::class.java).findFirst()

  override fun getUserNote(id: String): Note? {
    getUser()?.let { user ->
      return ListUtils.firstMatch(user.notes) { listItem -> listItem.uuid == id }
    } ?: return null
  }

  override fun saveUserNote(note: Note) {
    getUser()?.let { user ->
      transaction { realm ->
        ListUtils.addOrUpdateAll(user.notes,
            listOf(note)) { note1, note2 -> note1.uuid == note2.uuid }
        realm.copyToRealmOrUpdate(DbUtils.userToDbUser(user))
      }
    }

  }

  override fun deleteUserNote(id: String) {
    transaction { it.where(DbNote::class.java).equalTo("uuid", id).findFirst()?.deleteFromRealm() }
  }

  override fun getUpdatedValues(oldNotes: MutableList<Note>): MutableList<Note> {
    val newItems = mutableListOf<Note>()
    oldNotes.forEach { item ->
      provider.get().where(DbNote::class.java).equalTo("uuid", item.uuid).findFirst()?.let { newValue ->
        newItems.add(DbUtils.dbNoteToNote(newValue))
      }
    }
    return newItems
  }

  private fun transaction(session: (Realm) -> Unit) {
    provider.get().use { relm ->
      relm.executeTransaction { realm ->
        session(realm)
      }
    }
  }

}