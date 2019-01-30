package ir.easazade.dailynotes.utils

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import ir.easazade.dailynotes.BuildConfig
import ir.easazade.dailynotes.R

class AndroidUtils {
  companion object {
    fun showSnackBarMessage(
      activity: FragmentActivity,
      resIdString: Int,
      rootViewId: Int,
      durationMillis: Int? = null,
      actionName: String? = null,
      actionTextColorId: Int? = null,
      action: (() -> Unit)? = null
    ) {
      val root: View = activity.findViewById(rootViewId)
      val snackbar = Snackbar.make(root, resIdString, Snackbar.LENGTH_SHORT)
      durationMillis?.let { millis -> snackbar.duration = millis }
      if (action != null && actionName != null) {
        snackbar.setAction(actionName) {
          action.invoke()
        }
        actionTextColorId?.let { colorId ->
          snackbar.setActionTextColor(ContextCompat.getColor(activity, colorId))
        }
      }
      snackbar.view.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAccent))
      val snackText = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
      snackText.typeface = Typeface.createFromAsset(activity.assets, "karla.ttf")
      snackText.setTextColor(ContextCompat.getColor(activity, R.color.dark))
      snackbar.show()
    }

    fun showSnackBarMessage(
      activity: FragmentActivity,
      msg: String,
      rootViewId: Int,
      durationMillis: Int? = null,
      actionName: String? = null,
      actionTextColorId: Int? = null,
      action: (() -> Unit)? = null
    ) {
      val root: View = activity.findViewById(rootViewId)
      val snackbar = Snackbar.make(root, msg, Snackbar.LENGTH_SHORT)
      durationMillis?.let { millis -> snackbar.duration = millis }
      if (action != null && actionName != null) {
        snackbar.setAction(actionName) {
          action.invoke()
        }
        actionTextColorId?.let { colorId ->
          snackbar.setActionTextColor(ContextCompat.getColor(activity, colorId))
        }
      }
      snackbar.view.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAccent))
      val snackText =
          snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
      snackText.typeface = Typeface.createFromAsset(activity.assets, "karla.ttf")
      snackText.setTextColor(ContextCompat.getColor(activity, R.color.dark))
      snackbar.show()
    }

    fun openSettings(activity: FragmentActivity) {
      val intent = Intent()
      intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
      val uri = Uri.fromParts("package", activity.packageName, null)
      intent.data = uri
      activity.startActivity(intent)
    }

    fun log(value: Any) {
      if (BuildConfig.DEBUG) {
        Log.i("mars_app", value.toString())
      }
    }

  }

}