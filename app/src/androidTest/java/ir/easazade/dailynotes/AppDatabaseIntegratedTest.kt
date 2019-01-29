package ir.easazade.dailynotes

import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.realm.Realm
import io.realm.RealmConfiguration
import ir.easazade.dailynotes.businesslogic.database.AppDatabase
import ir.easazade.dailynotes.businesslogic.database.RealmProvider
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseIntegratedTest {

//    @get:Rule
//    var mActivityTestRule = ActivityTestRule<UnitTestEmptyActivity>(UnitTestEmptyActivity::class.java)

    lateinit var fakeUser: User
    lateinit var fakeNote: Note

    lateinit var database: AppDatabase

    var provider = object : RealmProvider {
        init {
            Realm.init(InstrumentationRegistry.getTargetContext())
        }
        val config = RealmConfiguration.Builder()
            .name("fake_app_database_test")
            .inMemory()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded().build()

        override fun get(): Realm = Realm.getInstance(config)
    }


    @Before
    fun setup() {
        fakeUser = FakeDataIntegrated.fakeUsers(1)[0]
        fakeNote = FakeDataIntegrated.fakeNotes(1)[0]
        database = AppDatabase.createInstance(provider)
    }

    @After
    fun tearDown() {
        provider.get().executeTransaction { it.deleteAll() }
    }

    @Test
    fun dummyTest() {
        assertTrue(true)
    }


}





