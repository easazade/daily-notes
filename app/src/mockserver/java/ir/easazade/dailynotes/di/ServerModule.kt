package ir.easazade.dailynotes.di

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import ir.easazade.dailynotes.businesslogic.database.RealmProvider
import ir.easazade.dailynotes.businesslogic.entities.DbNote
import ir.easazade.dailynotes.businesslogic.entities.DbUser
import ir.easazade.dailynotes.businesslogic.server.AppServer
import ir.easazade.dailynotes.businesslogic.server.IAppServer
import ir.easazade.dailynotes.utils.DateUtils.Companion.currentTime
import java.util.UUID

class ServerModule : IServerModule {

  private var provider: RealmProvider? = null

  private fun realmProvider(): RealmProvider {
    return if (provider != null)
      provider!!
    else {
      provider = object : RealmProvider {
        private val config = RealmConfiguration.Builder()
            .name("fake-server-database")
            .schemaVersion(1)
            .initialData { realm ->
              val userId = UUID.randomUUID()
                  .toString()
              val content = "lorem ipsum is a fake text made to see the design " +
                  "and result of development process it does not mean "
              val note1 = DbNote(
                  UUID.randomUUID().toString(),
                  userId, "title1", content, currentTime(), currentTime(), "#7BDB74"
              )
              val note2 = DbNote(
                  UUID.randomUUID().toString(),
                  userId, "title1", content, currentTime(), currentTime(), "#CC6D6D"
              )
              val note3 = DbNote(
                  UUID.randomUUID().toString(),
                  userId, "title1", content, currentTime(), currentTime(), "#5FB1CB"
              )
              val user = DbUser(
                  userId,
                  "alireza",
                  "easazade@gmail.com",
                  RealmList(note1, note2, note3)
              )
              realm.copyToRealmOrUpdate(user)
            }
            .deleteRealmIfMigrationNeeded()
            .build()

        override fun get(): Realm = Realm.getInstance(config)
      }
      provider!!
    }
  }

  override fun server(): IAppServer = AppServer(realmProvider())
}