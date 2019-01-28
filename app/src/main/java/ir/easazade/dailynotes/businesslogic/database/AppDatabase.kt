package ir.easazade.dailynotes.businesslogic.database

class AppDatabase private constructor(provider: RealmProvider):IAppDatabase {

    companion object {
        fun createInstance(provider: RealmProvider): AppDatabase = AppDatabase(provider)
    }





}