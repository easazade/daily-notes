package ir.easazade.dailynotes.screens.viewobjects

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import ir.easazade.dailynotes.R

class AddNoteDialog : FrameLayout {

  constructor(context: Context) : super(context)
  constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

  init {
    inflate(context, R.layout.view_add_note, this)
  }

}