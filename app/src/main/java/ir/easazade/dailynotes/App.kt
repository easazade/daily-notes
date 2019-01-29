package ir.easazade.dailynotes

import android.app.Application
import androidx.fragment.app.FragmentActivity
import io.realm.Realm
import ir.easazade.dailynotes.di.IAppComponent

class App : Application() {

  override fun onCreate() {
    Realm.init(this)
    super.onCreate()
  }

  fun isAppComponentSet() = appComponent != null

  companion object {
    fun get(activity: FragmentActivity): App = activity.application as App
    private var appComponent: IAppComponent? = null
    fun component() = appComponent!!
  }

  fun setAppComponent(component: IAppComponent) {
    appComponent = component
  }

}

