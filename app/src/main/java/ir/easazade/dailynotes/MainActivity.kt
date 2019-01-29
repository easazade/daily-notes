package ir.easazade.dailynotes

import android.os.Bundle
import ir.easazade.dailynotes.di.AppComponent
import ir.easazade.dailynotes.di.ArchitectureModule
import ir.easazade.dailynotes.di.DatabaseModule
import ir.easazade.dailynotes.di.ServerModule
import ir.easazade.dailynotes.sdk.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //setting app component
        App.get(this).setAppComponent(
            AppComponent(
                DatabaseModule(),
                ServerModule(),
                ArchitectureModule()
            )
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
