package ir.easazade.dailynotes.viewmodels

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.repos.INotesRepository
import ir.easazade.dailynotes.businesslogic.states.NState
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

  lateinit var createNoteTaskProgress: TestObserver<Boolean>
  lateinit var createNoteTaskSuccess: TestObserver<Note>
  lateinit var createNoteTaskFailure: TestObserver<Boolean>

  lateinit var editNoteTaskProgress: TestObserver<Boolean>
  lateinit var editNoteTaskSuccess: TestObserver<Note>
  lateinit var editNoteTaskFailure: TestObserver<Boolean>

  lateinit var deleteNoteTaskProgress: TestObserver<Boolean>
  lateinit var deleteNoteTaskSuccess: TestObserver<Boolean>
  lateinit var deleteNoteTaskFailure: TestObserver<Boolean>

  @Before
  fun setup() {
    repo = mockk()
    vm = NotesViewModel(repo, Schedulers.trampoline(), Schedulers.trampoline())
    fakes = FakeValues()

    //testobservers

    createNoteTaskProgress = vm.createNoteTask.progress.test()
    createNoteTaskSuccess = vm.createNoteTask.success.test()
    createNoteTaskFailure = vm.createNoteTask.failed.test()

    editNoteTaskProgress = vm.editNoteTask.progress.test()
    editNoteTaskSuccess = vm.editNoteTask.success.test()
    editNoteTaskFailure = vm.editNoteTask.failed.test()

    deleteNoteTaskProgress = vm.deleteNoteTask.progress.test()
    deleteNoteTaskSuccess = vm.deleteNoteTask.success.test()
    deleteNoteTaskFailure = vm.deleteNoteTask.failed.test()

  }

  @After
  fun tearDown() {

  }

  @Test
  fun dummy() {

  }

  @Test
  fun test_createNote_shouldCreateNote() {
    //with
    every { repo.createNote(fakes.note) } returns Observable.just(NState.success(fakes.note))
    //when
    vm.createNote(fakes.note)
    //
    createNoteTaskFailure.assertNoValues()
    createNoteTaskSuccess.assertValue(fakes.note)
  }

  @Test
  fun test_editNote_shouldEditNote() {
    //with
    every { repo.editNote(any(), any(), any(), any()) } returns Observable.just(
        NState.success(fakes.note))
    //when
    vm.editNote("", "", "", 5542)
    //
    editNoteTaskFailure.assertNoValues()
    editNoteTaskSuccess.assertValue(fakes.note)
  }

  @Test
  fun test_deleteNote_shouldDeleteNote() {
    //with
    every { repo.deleteNote(fakes.note.uuid) } returns Observable.just(NState.deleted())
    //when
    vm.deleteNote(fakes.note.uuid)
    //
    deleteNoteTaskFailure.assertNoValues()
    deleteNoteTaskSuccess.assertValue(true)
  }

}