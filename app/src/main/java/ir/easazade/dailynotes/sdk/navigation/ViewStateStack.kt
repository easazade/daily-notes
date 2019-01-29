package ir.easazade.dailynotes.sdk.navigation

class ViewStateStack(private val mStack: MutableList<ViewState>) {

    fun saveState(viewState: ViewState) {
        if (viewState.isStackable())
            mStack.add(viewState)
    }

    /***
     * returns the last state of the given Frag is Any
     */
    fun <T : ViewState> popLastStateOf(viewStateClass: Class<T>, clearTop: Boolean): T? {
        var index = -1
        val state = mStack.lastOrNull { viewState ->
            viewState::class.java.isAssignableFrom(viewStateClass)
        }
        mStack.indexOf(state)
        if ((index != -1).and(clearTop))//clear top and popping the matching state
            for (i in index until mStack.size) {
                mStack.removeAt(i)
            }
        return if (state != null)
            (state as T)
        else
            null
    }

    /***
     * used for back actions in navigator
     */
    fun popLastState(): ViewState? =
            if (mStack.isNotEmpty())
                mStack.removeAt(mStack.lastIndex)
            else null

    fun clear() {
        mStack.clear()
    }

    fun getLastState(): ViewState? = mStack.lastOrNull()


}