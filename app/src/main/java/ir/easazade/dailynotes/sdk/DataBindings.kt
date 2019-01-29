package ir.easazade.dailynotes.sdk

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.businesslogic.entities.User

class DataBindings {

  private val notePipeline: PublishRelay<Note> = PublishRelay.create()
  private val userPipeline: PublishRelay<User> = PublishRelay.create()

  fun updateNote(note: Note) {
    notePipeline.accept(note)
  }

  fun updateUser(user: User) {
    userPipeline.accept(user)
  }

  fun bindNote(
    noteId: String,
    consumer: Consumer<Note>
  ): Disposable =
    notePipeline.filter { it.uuid == noteId }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(consumer)

  fun bindUser(
    userId: String,
    consumer: Consumer<User>
  ): Disposable =
    userPipeline.filter { it.uuid == userId }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(consumer)

}