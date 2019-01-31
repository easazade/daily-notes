package ir.easazade.dailynotes

import android.app.Application
import androidx.fragment.app.FragmentActivity
import io.realm.Realm
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

