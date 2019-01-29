package ir.easazade.dailynotes.di

import io.realm.Realm
import io.realm.RealmConfiguration
import ir.easazade.dailynotes.businesslogic.database.AppDatabase
import ir.easazade.dailynotes.businesslogic.database.RealmProvider

class DatabaseModule : IDatabaseModule {

  private var database: AppDatabase? = null

  init {
    val realmConfig = RealmConfiguration.Builder()
        .name("mars_app_database")
        .schemaVersion(11)
        .deleteRealmIfMigrationNeeded()
        .build()
    Realm.setDefaultConfiguration(realmConfig)
  }

  private fun realmProvider(): RealmProvider = object : RealmProvider {
    override fun get(): Realm = Realm.getDefaultInstance()
  }

  override fun appDatabase(): AppDatabase {
    return if (database != null)
      database!!
    else {
      database = AppDatabase.createInstance(realmProvider())
      database!!
    }
  }

}