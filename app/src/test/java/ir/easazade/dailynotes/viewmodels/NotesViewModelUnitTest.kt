package ir.easazade.dailynotes.viewmodels

import io.mockk.mockk
import io.reactivex.schedulers.Schedulers
import ir.easazade.dailynotes.businesslogic.repos.INotesRepository
import ir.easazade.dailynotes.helpers.FakeValues
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NotesViewModelUnitTest {

  lateinit var vm: NotesViewModel
  lateinit var repo: INotesRepository
  lateinit var fakes: FakeValues

  @Before
  fun setup() {
    repo = mockk()
    vm = NotesViewModel(repo, Schedulers.trampoline(), Schedulers.trampoline())
    fakes = FakeValues()
  }

  @After
  fun tearDown() {

  }

  @Test
  fun dummy() {

  }

  @Test
  fun test_createNote_shouldCreateNote() {

  }

  @Test
  fun test_editNote_shouldEditNote() {

  }

  @Test
  fun test_deleteNote_shouldDeleteNote() {

  }



}