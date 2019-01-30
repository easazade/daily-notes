package ir.easazade.dailynotes

import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.realm.Realm
import io.realm.RealmConfiguration
import ir.easazade.dailynotes.businesslogic.database.AppDatabase
import ir.easazade.dailynotes.businesslogic.database.DbUtils
import ir.easazade.dailynotes.businesslogic.database.RealmProvider
import ir.easazade.dailynotes.businesslogic.entities.AboutUs
import ir.easazade.dailynotes.businesslogic.entities.AuthInfo
import ir.easazade.dailynotes.businesslogic.entities.DbUser
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User
import ir.easazade.dailynotes.helpers.FakeDataIntegrated
import ir.easazade.dailynotes.utils.DateUtils.Companion.currentTime
import ir.easazade.dailynotes.utils.ListUtils
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

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

  @Test
  fun test_getUser_ShouldGetUser() {
    assertNull(database.getUser())
    provider.get().executeTransaction { it.copyToRealmOrUpdate(DbUtils.userToDbUser(fakeUser)) }
    assertNotNull(database.getUser())
    assertEquals(database.getUser()!!.uuid, fakeUser.uuid)
  }

  @Test
  fun test_getUser_shouldNotGetUserWhenThereIsNone() {
    assertNull(database.getUser())
  }

  @Test
  fun test_saveUser_shouldSaveOnlyOneUserInDatabase() {
    assertEquals(0, provider.get().where(DbUser::class.java).findAll().size)
    database.saveUser(fakeUser)
    assertEquals(1, provider.get().where(DbUser::class.java).findAll().size)
    //saving same user again
    database.saveUser(fakeUser)
    assertEquals(1, provider.get().where(DbUser::class.java).findAll().size)
    //saving a different user
    val anotherUser = fakeUser.copy(uuid = UUID.randomUUID().toString())
    database.saveUser(anotherUser)
    assertEquals(1, provider.get().where(DbUser::class.java).findAll().size)
  }

  @Test
  fun test_isLoggedIn_shouldBeLoggedIn() {
    assertFalse(database.isLoggedIn())
    database.saveUser(fakeUser)
    assertTrue(database.isLoggedIn())
  }

  @Test
  fun test_deleteUser_shouldDeleteUser() {
    assertNull(database.getUser())
    database.saveUser(fakeUser)
    assertNotNull(database.getUser())
    database.deleteUser()
    assertNull(database.getUser())
  }

  @Test
  fun test_saveAuthInfo__getAuthInfo_shouldSaveAuthInfoOnlyOneInstanceInDatabase() {
    val authInfo = AuthInfo("token", "bearer", currentTime())
    assertNull(database.getAuthInfo())
    database.saveAuthInfo(authInfo)
    assertNotNull(database.getAuthInfo())
    //saving another authInfo
    val anotherAuthInfo = AuthInfo("token 2", "bearer", currentTime())
    database.saveAuthInfo(anotherAuthInfo)
    assertEquals("token 2", database.getAuthInfo()!!.token)
  }

  @Test
  fun test_saveAboutUs_getAboutUs_shouldSaveAndGetOnlyOneInstanceInDatabase() {
    val aboutUs = AboutUs("novacode")
    assertNull(database.getAboutUs())
    database.saveAboutUs(aboutUs)
    assertNotNull(database.getAboutUs())
    //saving another authInfo
    val anotherAboutUs = AboutUs("morans")
    database.saveAboutUs(anotherAboutUs)
    assertEquals("morans", database.getAboutUs()!!.teamName)
  }

  @Test
  fun test_saveUserNote_shouldNoteAsOneOfUsersNoteInUserObject() {
    //with
    database.saveUser(fakeUser)
    val oldUserNotesCount = fakeUser.notes.size
    //when
    val newNote = fakeNote.copy(uuid = UUID.randomUUID().toString())
    database.saveUserNote(newNote)
    //then
    val newUserNotesCount = database.getUser()!!.notes.size
    assertEquals(oldUserNotesCount + 1, newUserNotesCount)
  }

  @Test
  fun test_getUserNote_shouldGetUserNoteWhenThereIs() {
    //with
    fakeUser.notes.add(fakeNote)
    database.saveUser(fakeUser)
    //then
    val result = database.getUserNote(fakeNote.uuid)
    assertNotNull(result)
    assertEquals(fakeNote.uuid, result!!.uuid)
  }

  @Test
  fun test_getUserNote_shouldGetUserNoteWhenThereIsNONE() {
    //with
    database.saveUser(fakeUser)
    //then
    val result = database.getUserNote(fakeNote.uuid)
    assertNull(result)
  }

  @Test
  fun test_deleteUserNote_shouldDeleteUserNote() {
    //with
    fakeUser.notes.add(fakeNote)
    database.saveUser(fakeUser)
    val originalUserNotesCount = fakeUser.notes.size
    //when
    database.deleteUserNote(fakeNote.uuid)
    //then
    val newUserNotesCount = database.getUser()!!.notes.size
    assertEquals(originalUserNotesCount - 1, newUserNotesCount)
    database.getUser()!!.notes.forEach {
      assertNotEquals(it.uuid, fakeNote.uuid)
    }
    // just testing if these Utility methods work fine
    // otherwise foreach assertion is enough
    assertNull(
        ListUtils.firstMatch(database.getUser()!!.notes) { n1 -> n1.uuid == fakeNote.uuid }
    )
    assertFalse(
        ListUtils.contains(database.getUser()!!.notes, fakeNote) { n1, n2 -> n1.uuid == n2.uuid }
    )
  }

}





