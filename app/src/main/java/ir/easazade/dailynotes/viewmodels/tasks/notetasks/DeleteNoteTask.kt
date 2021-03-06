package ir.easazade.dailynotes.viewmodels.tasks.notetasks

import com.jakewharton.rxrelay2.PublishRelay

class DeleteNoteTask {
  val progress = PublishRelay.create<Boolean>()
  val success = PublishRelay.create<String>()
  val failed = PublishRelay.create<String>()
}