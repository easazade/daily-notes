package ir.easazade.dailynotes.sdk.navigation

import androidx.fragment.app.Fragment
import ir.easazade.dailynotes.screens.LoginFrag
import ir.easazade.dailynotes.screens.HomeFrag
import ir.easazade.dailynotes.screens.NoteFrag
import ir.easazade.dailynotes.screens.SignupFrag
import ir.easazade.dailynotes.sdk.BaseActivity
import kotlinx.android.synthetic.main.frag_main.view.mMainTransparentLogo

class Navigator<HomeState : ViewState, HomeArg : Arguments> private constructor(
  private val activity: BaseActivity,
  private val placeHolder: Int,
  private var mStack: ViewStateStack,
  private val homeFragViewStateClass: Class<HomeState>,
  private val homeFragFactory: () -> BaseFrag<HomeState, HomeArg>,
  private val homeFragDefaultArgs: () -> HomeArg,
  private val isHomeFrag: (Fragment) -> Boolean,
  private val lockMenu: () -> Unit,
  private val unlockMenu: () -> Unit
) {

//    private val LAST_KNOWN_STATE = "9ajwdja0wd0-last_KnoWn_StAtE"
//
//    private val MAIN_FRAG_KEY = HomeFrag.State::class.java.simpleName
//    private val LOGIN_FRAG_KEY = LoginFrag.State::class.java.simpleName
//    private val SIGNUP_FRAG_KEY = SignUpFrag.State::class.java.simpleName
//    private val SEARCH_FRAG_KEY = SearchFrag.State::class.java.simpleName
//    private val FAVORITES_FRAG_KEY = FavoritesFrag.State::class.java.simpleName
//    private val CHANGEPASS_FRAG_KEY = ChangePassFrag.State::class.java.simpleName
//    private val DESIGN_FRAG_KEY = DesignFrag.State::class.java.simpleName
//    private val FORGOTPASS_FRAG_KEY = ForgotPassFrag.State::class.java.simpleName
//    private val LISTARTIST_FRAG_KEY = ListArtistFrag.State::class.java.simpleName
//    private val PROFILE_FRAG_KEY = ProfileFrag.State::class.java.simpleName
//    private val SETTINGS_FRAG_KEY = SettingsFrag.State::class.java.simpleName
//    private val UPLOAD_FRAG_KEY = UploadFrag.State::class.java.simpleName
//    private val MYDEISGNS_FRAG_KEY = MyDesignsFrag.State::class.java.simpleName
//    private val TEST1_FRAG_KEY = TestFrag1.State::class.java.simpleName
//    private val TEST2_FRAG_KEY = TestFrag2.State::class.java.simpleName
//    private val TEST3_FRAG_KEY = TestFrag3.State::class.java.simpleName
//    private val TESTHOME_FRAG_KEY = TestHomeFrag.State::class.java.simpleName

  companion object {
    fun <HomeState : ViewState, HomeArg : Arguments> create(
      activity: BaseActivity,
      placeHolder: Int,
      stack: ViewStateStack,
      homeFragViewStateClass: Class<HomeState>,
      homeFragFactory: () -> BaseFrag<HomeState, HomeArg>,
      homeFragDefaultArgs: () -> HomeArg,
      homeFragValidator: (Fragment) -> Boolean,
      lockMenu: () -> Unit,
      unlockMenu: () -> Unit
    ): Navigator<HomeState, HomeArg> {
      return Navigator(
          activity, placeHolder, stack, homeFragViewStateClass,
          homeFragFactory, homeFragDefaultArgs, homeFragValidator, lockMenu, unlockMenu
      )
    }
  }

//    fun persistCurrentState(bundle: Bundle) {
//        val fragment = activity.supportFragmentManager.findFragmentById(placeHolder)
//        fragment?.let {
//            val baseFrag = fragment as BaseFrag<ViewState, Arguments>
//            val currentState = baseFrag.getCurrentState()
//            when (currentState) {
//                is HomeFrag.State -> {
//                    bundle.putString(LAST_KNOWN_STATE, MAIN_FRAG_KEY)
//                    currentState.persist(bundle)
//                }
//                is DesignFrag.State -> {
//                    bundle.putString(LAST_KNOWN_STATE, DESIGN_FRAG_KEY)
//                    currentState.persist(bundle)
//                }
//                is ProfileFrag.State -> {
//                    bundle.putString(LAST_KNOWN_STATE, PROFILE_FRAG_KEY)
//                    currentState.persist(bundle)
//                }
//                is ListArtistFrag.State -> {
//                    bundle.putString(LAST_KNOWN_STATE, LISTARTIST_FRAG_KEY)
//                    currentState.persist(bundle)
//                }
//                is LoginFrag.State -> bundle.putString(LAST_KNOWN_STATE, LOGIN_FRAG_KEY)
//                is SearchFrag.State -> bundle.putString(LAST_KNOWN_STATE, SEARCH_FRAG_KEY)
//                is SignUpFrag.State -> bundle.putString(LAST_KNOWN_STATE, SIGNUP_FRAG_KEY)
//                is ChangePassFrag.State -> bundle.putString(LAST_KNOWN_STATE, CHANGEPASS_FRAG_KEY)
//                is UploadFrag.State -> bundle.putString(LAST_KNOWN_STATE, UPLOAD_FRAG_KEY)
//                is FavoritesFrag.State -> bundle.putString(LAST_KNOWN_STATE, FAVORITES_FRAG_KEY)
//                is ForgotPassFrag.State -> bundle.putString(LAST_KNOWN_STATE, FORGOTPASS_FRAG_KEY)
//                is SettingsFrag.State -> bundle.putString(LAST_KNOWN_STATE, SETTINGS_FRAG_KEY)
//                is MyDesignsFrag.State -> bundle.putString(LAST_KNOWN_STATE, MYDEISGNS_FRAG_KEY)
//                is TestFrag1.State -> bundle.putString(LAST_KNOWN_STATE, TEST1_FRAG_KEY)
//                is TestFrag2.State -> bundle.putString(LAST_KNOWN_STATE, TEST2_FRAG_KEY)
//                is TestFrag3.State -> bundle.putString(LAST_KNOWN_STATE, TEST3_FRAG_KEY)
//                is TestHomeFrag.State -> bundle.putString(LAST_KNOWN_STATE, TESTHOME_FRAG_KEY)
//            }
//
//        }
//
//    }

//    fun restoreLastState(bundle: Bundle) {
//        val key = bundle.getString(LAST_KNOWN_STATE)
//        var viewState: ViewState? = null
//        if (key != null) {
//            when (key) {
//                MAIN_FRAG_KEY -> viewState = HomeFrag.State.createFrom(bundle)
//                DESIGN_FRAG_KEY -> viewState = DesignFrag.State.createFrom(bundle)
//                PROFILE_FRAG_KEY -> viewState = ProfileFrag.State.createFrom(bundle)
//                LISTARTIST_FRAG_KEY -> viewState = ListArtistFrag.State.createFrom(bundle)
//                LOGIN_FRAG_KEY -> viewState = LoginFrag.State.createFrom(bundle)
//                SEARCH_FRAG_KEY -> viewState =
//                SIGNUP_FRAG_KEY
//                CHANGEPASS_FRAG_KEY
//                UPLOAD_FRAG_KEY
//                FAVORITES_FRAG_KEY
//                FORGOTPASS_FRAG_KEY
//                SETTINGS_FRAG_KEY
//                MYDEISGNS_FRAG_KEY
//                TEST1_FRAG_KEY
//                TEST2_FRAG_KEY
//                TEST3_FRAG_KEY
//                TESTHOME_FRAG_KEY
//
//                else -> destination(HomeFrag()).withArguments(HomeFrag.Args()).go()
//            }
//        }
//        navigateToDestinationFromViewState(viewState, false)
//    }

  fun back(saveCurrentFragState: Boolean = false) {
    val viewState = mStack.popLastState()
    navigateToDestinationFromViewState(viewState, saveCurrentFragState)

  }

  private fun navigateToDestinationFromViewState(
    viewState: ViewState?,
    saveCurrentFragState: Boolean
  ) {
    if (viewState != null) {
      if (viewState::class.java.isAssignableFrom(homeFragViewStateClass)) {
        mStack.clear()
        destination(homeFragFactory.invoke()).withState(viewState as HomeState)
            .go()
      } else {
        when (viewState) {
          is LoginFrag.State -> destination(
              LoginFrag()).withState(viewState).go(
              saveCurrentFragState
          )
          is NoteFrag.State -> destination(NoteFrag()).withState(viewState).go(saveCurrentFragState)
          is SignupFrag.State -> destination(SignupFrag()).withState(viewState).go(
              saveCurrentFragState)
          else -> {
            activity.moveTaskToBack(true)
          }
        }
      }
    } else if (activity.supportFragmentManager.findFragmentById(placeHolder)
            ?.let { isHomeFrag(it) } == true
    ) {
      activity.moveTaskToBack(true)
    } else if (activity.supportFragmentManager.findFragmentById(placeHolder)
            ?.let { it is LoginFrag } == true
    ) {
      activity.moveTaskToBack(true)
    } else {
      destination(homeFragFactory()).withArguments(homeFragDefaultArgs())
          .go()
    }
  }

  fun <T : BaseFrag<S, R>, S : ViewState, R : Arguments> destination(baseFragment: T): ConfigBuilder<T, S, R> {
    return ConfigBuilder(baseFragment, activity, placeHolder, this)
  }

  fun forceClearBackStack() {
    mStack.clear()
  }

//    fun <S : ViewState> searchForLastStateOf(viewStateClass: Class<S>) =
//            mStack.popLastStateOf(viewStateClass,false)

  class ConfigBuilder<T : BaseFrag<S, R>, S : ViewState, R : Arguments>(
    private val frag: BaseFrag<S, R>,
    private val activity: BaseActivity,
    private val placeHolderResId: Int,
    private val navigator: Navigator<*, *>
  ) {

    fun withState(
      state: S,
      updateStateValues: Boolean = true
    ): NavigationAction<T, S, R> {
      var pageState = state
      if (updateStateValues)
        pageState = pageState.createUpdate() as S
      frag.viewState = pageState
      frag.args = pageState.args as R
      return NavigationAction(frag, activity, placeHolderResId, navigator)
    }

    fun withArguments(args: R): NavigationAction<T, S, R> {
      frag.args = args
      return NavigationAction(frag, activity, placeHolderResId, navigator)
    }

    /***
     * this method is similar to back button but to navigate up the hierarchy of
     * pages in our application
     *
     * this method will clears the stack up to the last viewState of the given type
     * and if no viewState of the type is found in stack it will lunch mainFrag with
     * lastState if any
     */
    fun backToLastInstanceIfAnyAny(
      holderClass: Class<S>,
      clearTop: Boolean = true
    ): NavigationAction<T, S, R> {
      val lastState = navigator.mStack.popLastStateOf(holderClass, clearTop)
      return if (lastState != null) {
        frag.args = lastState.args as R
        frag.viewState = lastState
        NavigationAction(frag, activity, placeHolderResId, navigator)
      } else {
        if (holderClass.isAssignableFrom(HomeFrag::class.java)) {
          navigator.destination(HomeFrag())
              .withArguments(HomeFrag.Args())
              .go()
        } else {
          navigator.destination(HomeFrag())
              .backToLastInstanceIfAnyAny(HomeFrag.State::class.java)
              .go()
        }
        NavigationAction(frag, activity, placeHolderResId, navigator)
      }
    }

  }

  class NavigationAction<T : BaseFrag<S, R>, S : ViewState, R : Arguments>(
    private val frag: BaseFrag<S, R>,
    private val activity: BaseActivity,
    private val placeHolderResId: Int,
    private val navigator: Navigator<*, *>
  ) {
    fun go(saveStateOfCurrentFrag: Boolean = true) {
      if (saveStateOfCurrentFrag) {
        val fragment = activity.supportFragmentManager.findFragmentById(placeHolderResId)
        fragment?.let {
          val baseFrag = fragment as BaseFrag<ViewState, Arguments>
          navigator.mStack.saveState(baseFrag.getCurrentState())
        }
      }
      if (navigator.isHomeFrag.invoke(frag)) {
        navigator.mStack.clear()
      }
      activity.supportFragmentManager
          .beginTransaction()
          .replace(placeHolderResId, frag)
          .commit()
      if (frag is HomeFrag)
        navigator.unlockMenu()
      else
        navigator.lockMenu()
    }

    fun <T : BaseFrag<S, R>> goIfNotThere(
      fragClass: Class<T>,
      saveStateOfCurrentFrag: Boolean = true
    ) {
      val fragment = activity.supportFragmentManager.findFragmentById(placeHolderResId)
      fragment?.let { fragg ->
        val baseFrag = fragg as BaseFrag<ViewState, Arguments>
        if (!baseFrag::class.java.isAssignableFrom(fragClass)) {
          if (saveStateOfCurrentFrag)
            navigator.mStack.saveState(baseFrag.getCurrentState())
          activity.supportFragmentManager
              .beginTransaction()
              .replace(placeHolderResId, frag)
              .commit()
          if (frag is HomeFrag)
            navigator.unlockMenu()
          else
            navigator.lockMenu()
        }
      }

    }
  }
}