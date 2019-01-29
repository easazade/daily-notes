package ir.easazade.dailynotes.di

import ir.easazade.dailynotes.R
import ir.easazade.dailynotes.screens.main.HomeFrag
import ir.easazade.dailynotes.sdk.BaseActivity
import ir.easazade.dailynotes.sdk.DataBindings
import ir.easazade.dailynotes.sdk.navigation.Navigator
import ir.easazade.dailynotes.sdk.navigation.ViewStateStack

class AndroidModule(private val activity: BaseActivity) : IAndroidModule {

  private var mDataBindings: DataBindings? = null
  private var navigator: Navigator<HomeFrag.State, HomeFrag.Args>? = null

  override fun databindings(): DataBindings {
    return if (mDataBindings != null)
      mDataBindings!!
    else {
      mDataBindings = DataBindings()
      mDataBindings!!
    }
  }

  override fun navigator(): Navigator<HomeFrag.State, HomeFrag.Args> {
    return if (navigator != null)
      navigator!!
    else {
      navigator =
        Navigator.create(activity, R.id.MAIN_PLACE_HOLDER, ViewStateStack(mutableListOf()),
            HomeFrag.State::class.java,
            { HomeFrag() },
            { HomeFrag.Args() },
            { it is HomeFrag },
            {},
            {}
        )
      return navigator!!
    }
  }

}