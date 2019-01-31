package ir.easazade.dailynotes

import android.app.Application
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity
import com.jakewharton.rxrelay2.PublishRelay
import io.realm.Realm
import ir.easazade.dailynotes.businesslogic.system.ConnectivityChangeReceiver
import ir.easazade.dailynotes.di.IAppComponent
import ir.easazade.dailynotes.utils.AppReleaseTree
import timber.log.Timber

class App : Application() {

  override fun onCreate() {
    Realm.init(this)
    if (BuildConfig.DEBUG) {
      Timber.plant(object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
          return super.createStackElementTag(element) + ":${element.lineNumber}"
        }
      })
    } else {
      //Crashlytics.init()
      Timber.plant(AppReleaseTree())
    }
    //registering for connectivity changes
    val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    this.applicationContext.registerReceiver(ConnectivityChangeReceiver(),intentFilter)
    super.onCreate()
  }

  fun getAppVersion(): String {
    try {
      val pInfo = packageManager.getPackageInfo(packageName, 0)
      return pInfo.versionName
    } catch (nne: PackageManager.NameNotFoundException) {
//      Crashlytics.logException(nne)
    } catch (e: Exception) {
//      Crashlytics.logException(e)
    }
    return ""
  }

  fun isAppComponentSet() = appComponent != null

  companion object {
    val connected = PublishRelay.create<Boolean>()
    fun get(activity: FragmentActivity): App = activity.application as App
    private var appComponent: IAppComponent? = null
    fun component() = appComponent!!
  }

  fun setAppComponent(component: IAppComponent) {
    appComponent = component
  }

}

