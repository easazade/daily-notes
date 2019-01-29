package ir.easazade.dailynotes.sdk

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.viewmodels.UserViewModel

const val APP_COMPONENT = "app_component"

abstract class BaseActivity : AppCompatActivity() {

  var userViewModel: UserViewModel? = null

  fun userVm() = userViewModel!!

  fun hideSoftKeyboard() {
    try {
      val inputMethodManager = getSystemService(
          Activity.INPUT_METHOD_SERVICE
      ) as InputMethodManager
      inputMethodManager.hideSoftInputFromWindow(
          currentFocus!!.windowToken, 0
      )
    } catch (e: Exception) {
//            Crashlytics.logException(e)
    }
  }

  override fun getSystemService(name: String): Any {
    if (name == APP_COMPONENT)
      return App.component()
    return super.getSystemService(name)
  }

  override fun attachBaseContext(newBase: Context?) {
    super.attachBaseContext(AppContextWrapper.wrap(newBase))
  }

  override fun onBackPressed() {
    App.component()
        .navigator()
        .back()
  }

}