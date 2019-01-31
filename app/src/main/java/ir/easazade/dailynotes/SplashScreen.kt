package ir.easazade.dailynotes

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.concurrent.TimeUnit

class SplashScreen : AppCompatActivity() {

  private val subscriptions = CompositeDisposable()
  private val mHideHandler = Handler()
  private var askedForPermissionBefore = false
  private val mHidePart2Runnable = Runnable {
    // Delayed removal of status and navigator bar

    // Note that some of these constants are new as of API 16 (Jelly Bean)
    // and API 19 (KitKat). It is safe to use them, as they are inlined
    // at compile-time and do nothing on earlier devices.
    mSplashLogo.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LOW_PROFILE or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
  }
  private val mShowPart2Runnable = Runnable {
    // Delayed display of UI elements
    supportActionBar?.show()
//        fullscreen_content_controls.visibility = View.VISIBLE
  }
  private var mVisible: Boolean = false
  private val mHideRunnable = Runnable { hide() }

  /**
   * Touch listener to use for in-layout UI controls to delay hiding the
   * system UI. This is to prevent the jarring behavior of controls going away
   * while interacting with activity UI.
   */
  private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
    if (AUTO_HIDE) {
      delayedHide(AUTO_HIDE_DELAY_MILLIS)
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//        Fabric.with(this, Crashlytics())
    setContentView(R.layout.activity_splash_screen)
    mSplashVersion.text = App.get(this).getAppVersion()
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    hide()
    mVisible = true

    // Set up the user interaction to manually show or hide the system UI.
    mSplashLogo.setOnClickListener { toggle() }

    // Upon interacting with UI controls, delay any scheduled hide()
    // operations to prevent the jarring behavior of controls going away
    // while interacting with the UI.
    // dummy_button.setOnTouchListener(mDelayHideTouchListener)
    logoAnimate(mSplashLogo, mSplashVersion)

  }

  override fun onResume() {
    val intent = Intent(this, MainActivity::class.java)
    subscriptions.add(
        Observable.just(1)
            .delay(4000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
              startActivity(intent)
              finish()
            }
    )
    super.onResume()
  }

  override fun onPause() {
    subscriptions.clear()
    super.onPause()
  }

  private fun logoAnimate(logo: View, version: View) {
    val versionAnim = ObjectAnimator
        .ofFloat(version, "alpha", 0f, 1f).apply {
          duration = 500
          interpolator = FastOutSlowInInterpolator()
          startDelay = 500
          addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
              version.visibility = View.VISIBLE
            }
          })
        }
    val logoAlphaAnim = ObjectAnimator
        .ofFloat(logo, "alpha", 0f, 1f).apply {
          duration = 1000
          interpolator = FastOutSlowInInterpolator()
          startDelay = 500
          addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
              logo.visibility = View.VISIBLE
            }
          })
        }
    val animSet = AnimatorSet()
    animSet.play(logoAlphaAnim).before(versionAnim)
    animSet.start()
  }

  private fun toggle() {
    if (mVisible) {
      hide()
    } else {
      show()
    }
  }

  private fun hide() {
    // Hide UI first
    supportActionBar?.hide()
//        fullscreen_content_controls.visibility = View.GONE
    mVisible = false

    // Schedule a runnable to remove the status and navigator bar after a delay
    mHideHandler.removeCallbacks(mShowPart2Runnable)
    mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
  }

  private fun show() {
    // Show the system bar
    mSplashLogo.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    mVisible = true

    // Schedule a runnable to display UI elements after a delay
    mHideHandler.removeCallbacks(mHidePart2Runnable)
    mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
  }

  /**
   * Schedules a call to hide() in [delayMillis], canceling any
   * previously scheduled calls.
   */
  private fun delayedHide(delayMillis: Int) {
    mHideHandler.removeCallbacks(mHideRunnable)
    mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
  }

  companion object {
    /**
     * Whether or not the system UI should be auto-hidden after
     * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
     */
    private val AUTO_HIDE = true

    /**
     * If [AUTO_HIDE] is setFab, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private val AUTO_HIDE_DELAY_MILLIS = 3000

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigator bar.
     */
    private val UI_ANIMATION_DELAY = 300
  }
}