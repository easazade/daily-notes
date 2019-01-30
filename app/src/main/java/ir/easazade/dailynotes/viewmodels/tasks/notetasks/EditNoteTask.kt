package ir.easazade.dailynotes.viewmodels.tasks.notetasks

import com.jakewharton.rxrelay2.PublishRelay
import ir.easazade.dailynotes.businesslogic.entities.Note

class EditNoteTask{
  val progress = PublishRelay.create<Boolean>()
  val success = PublishRelay.create<Note>()
  val failed = PublishRelay.create<String>()
}