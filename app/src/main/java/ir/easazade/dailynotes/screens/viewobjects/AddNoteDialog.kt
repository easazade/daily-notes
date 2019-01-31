package ir.easazade.dailynotes.screens.viewobjects

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.MainActivity
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.utils.DateUtils.Companion.currentTime
import ir.easazade.dailynotes.utils.Roller
import java.util.UUID

class AddNoteDialog : FrameLayout {

  val colorRoller = Roller(
      "#95f69c",
      "#8e569c",
      "#cf669c",
      "#caf69c"
  )

  private val mSaveBtn: View
  private val mBackBtn: ImageView
  private val mTitle: EditText
  private val mContent: EditText
  private var mShouldBeShown = false
  var state = State.HIDDEN

  enum class State {
    HIDDEN,
    VISIBLE,
    ANIMATING
  }

  companion object {
    private const val destination = -2000f
  }

  constructor(context: Context) : super(context)
  constructor(
    context: Context,
    attributeSet: AttributeSet
  ) : super(context, attributeSet) {
    val array = context.theme
        .obtainStyledAttributes(attributeSet, R.styleable.AddNoteDialog, 0, 0)
    val stateValue = array.getInt(R.styleable.AddNoteDialog_anv_visibility_state, 0)
    when (stateValue) {
      0 -> {
        state = State.HIDDEN
        translationX = destination
      }
      1 -> {
        state = State.VISIBLE
        translationX = 0f
      }
    }
    array.recycle()
  }

  init {
    inflate(context, R.layout.view_add_note, this)
    mSaveBtn = findViewById(R.id.mAddNoteSaveBtn)
    mTitle = findViewById(R.id.mAddNoteTitle)
    mContent = findViewById(R.id.mAddNoteContent)
    mBackBtn = findViewById(R.id.mAddNoteBackBtn)
    mBackBtn.setOnClickListener { hide() }
    //setting viewmodels
    mSaveBtn.setOnClickListener { _ ->
      if (context != null) {
        (context as MainActivity).notesVm().createNote(Note(
            UUID.randomUUID().toString(),
            App.component().database().getUser()!!.uuid,
            mTitle.text.toString(),
            mContent.text.toString(),
            currentTime(),
            currentTime(),
            colorRoller.roll()
        ))
      }
      hide()
    }
  }

  fun clearContent() {
    mTitle.setText("")
    mContent.setText("")
  }

  fun setContent(title: String, content: String) {
    mTitle.setText(title)
    mContent.setText(content)
  }

  fun hide() {
    mShouldBeShown = false
    doHideAction()
  }

  fun show() {
    mShouldBeShown = true
    doShowAction()
  }

  private fun doHideAction() {
    try {
      if (state == State.VISIBLE && state != State.ANIMATING) {
        val anim = ObjectAnimator
            .ofFloat(this, "translationX", 0f, destination)
            .apply {
              interpolator = FastOutSlowInInterpolator()
            }
        val set = AnimatorSet()
        set.play(anim)
        set.addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationStart(animation: Animator?) {
            state = State.ANIMATING
          }

          override fun onAnimationEnd(animation: Animator?) {
            translationX = destination
            state = State.HIDDEN
            if (mShouldBeShown)
              doShowAction()
          }
        })
        set.start()
      }
    } catch (e: Exception) {
//      Crashlytics.logException(e)
    }
  }

  private fun doShowAction() {
    try {
      if (state == State.HIDDEN && state != State.ANIMATING) {
        val wholeViewAnim = ObjectAnimator
            .ofFloat(this, "translationX", destination, 0f)
            .apply {
              interpolator = FastOutSlowInInterpolator()

            }
        val set = AnimatorSet()
        set.play(wholeViewAnim)
        set.addListener(object : AnimatorListenerAdapter() {
          override fun onAnimationStart(animation: Animator?) {
            state = State.ANIMATING
          }

          override fun onAnimationEnd(animation: Animator?) {
            translationX = 0f
            state = State.VISIBLE
            if (!mShouldBeShown)
              doHideAction()
          }
        })
        set.start()
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

}