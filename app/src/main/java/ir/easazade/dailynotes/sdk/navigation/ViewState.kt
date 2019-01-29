package ir.easazade.dailynotes.sdk.navigation

abstract class ViewState(open val args: Arguments) {

    fun isStackable(): Boolean = true

    abstract fun createUpdate(): ViewState

}