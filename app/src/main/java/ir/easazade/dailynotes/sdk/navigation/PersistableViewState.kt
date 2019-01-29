package ir.easazade.dailynotes.sdk.navigation

import android.os.Bundle

abstract class PersistableViewState(args: Arguments) : ViewState(args) {

    abstract fun persist(bundle: Bundle)

}