package ir.easazade.dailynotes

import android.os.Bundle
import ir.easazade.dailynotes.sdk.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
