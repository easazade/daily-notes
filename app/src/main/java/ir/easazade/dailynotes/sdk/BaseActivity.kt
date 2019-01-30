package ir.easazade.dailynotes.sdk

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import ir.easazade.dailynotes.App
import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.utils.AndroidUtils
import ir.easazade.dailynotes.viewmodels.NotesViewModel
import ir.easazade.dailynotes.viewmodels.UserViewModel

const val APP_COMPONENT = "app_component"

abstract class BaseActivity : AppCompatActivity() {

  val subscriptions = CompositeDisposable()

  var userViewModel: UserViewModel? = null
  var notesViewModel: NotesViewModel? = null

  fun userVm() = userViewModel!!
  fun notesVm() = notesViewModel!!

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

  fun showSnackBarWithErrorMsg(errorMsg: String) {
    AndroidUtils.showSnackBarMessage(this, errorMsg, R.id.mainActivity_root)
  }

  fun showNoInternetConnectionSnackBar() {
    AndroidUtils.showSnackBarMessage(this, R.string.no_connection, R.id.mainActivity_root)
  }

  fun showDevelopingMessageSnackBar() {
    AndroidUtils.showSnackBarMessage(
        this@BaseActivity,
        R.string.in_development,
        R.id.mainActivity_root
    )
  }

  fun showNetworkErrorMessageSnackBar() {
    AndroidUtils.showSnackBarMessage(this, R.string.network_error, R.id.mainActivity_root)
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