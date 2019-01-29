package ir.easazade.dailynotes.businesslogic.database

import ir.easazade.dailynotes.businesslogic.entities.*
import ir.easazade.dailynotes.utils.ListUtils

class AppDatabase private constructor(private val provider: RealmProvider) : IAppDatabase {

    companion object {
        fun createInstance(provider: RealmProvider): AppDatabase = AppDatabase(provider)
    }

    override fun getUser(): User? {
        provider.get().use { relm ->
            relm.where(DbUser::class.java).findFirst()?.let {
                return DbUtils.dbUserToUser(it)
            } ?: return null
        }
    }

    override fun saveAuthInfo(authInfo: AuthInfo) {
        provider.get().use { relm ->
            relm.executeTransaction { realm ->
                realm.copyToRealmOrUpdate(authInfo)
            }
        }
    }

    override fun getAuthInfo(): AuthInfo? = provider.get().where(AuthInfo::class.java).findFirst()


    override fun saveAboutUs(aboutUs: AboutUs) {
        provider.get().use { relm ->
            relm.executeTransaction { realm ->
                realm.copyToRealmOrUpdate(aboutUs)
            }
        }
    }

    override fun getAboutUs(): AboutUs? = provider.get().where(AboutUs::class.java).findFirst()

    override fun getUserNote(id: String): Note? {
        getUser()?.let { user ->
            return ListUtils.firstMatch(user.notes) { listItem -> listItem.uuid == id }
        } ?: return null
    }

    override fun saveUserNote(note: Note) {
        provider.get().use { relm ->
            relm.executeTransaction { realm ->
                realm.copyToRealmOrUpdate(DbUtils.noteToDbNote(note))
            }
        }
    }

    override fun deleteUserNote(id: String) {
        provider.get().use { relm ->
            relm.executeTransaction { realm ->
                realm.where(DbNote::class.java).findFirst()?.deleteFromRealm()
            }
        }
    }


}