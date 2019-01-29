package ir.easazade.dailynotes.businesslogic.database

class AppDatabase private constructor(provider: RealmProvider) {

    companion object {
        fun createInstance(provider: RealmProvider): AppDatabase = AppDatabase(provider)
    }


}