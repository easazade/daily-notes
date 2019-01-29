package ir.easazade.dailynotes.sdk

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*

class AppContextWrapper {

    companion object {

        fun wrap(base: Context?): Context? {
            //caligraphy setting default font
            var context: Context? = base

            //setFab fixed locale
            //since this app has only one language
            val locale = Locale("en")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context?.createConfigurationContext(config)
            } else {
                context?.resources?.updateConfiguration(config, context?.resources?.displayMetrics)
            }
            //context = CalligraphyContextWrapper.wrap(context)
            return context
        }
    }

}