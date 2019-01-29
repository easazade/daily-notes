package ir.easazade.dailynotes.sdk.navigation

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ir.easazade.dailynotes.sdk.BaseActivity

abstract class BaseFrag<S : ViewState, R : Arguments> : Fragment() {

    lateinit var mActivity: BaseActivity
    var viewState: S? = null
    lateinit var args: R
    var fragSubscriptions = CompositeDisposable()
    var ui: Scheduler = AndroidSchedulers.mainThread()

    abstract fun getArgumentsAndSetProperties(args: R)

    abstract fun initializeViews()

    open fun onLoadPreviousState(stateHolder: S?) {
        onLoadNewState(args)
    }

    abstract fun onLoadNewState(args: R)

    abstract fun getCurrentState(): S

    abstract fun observeViewModels()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
        getArgumentsAndSetProperties(args)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeViews()
        if (viewState != null) {
            onLoadPreviousState(viewState)
        } else {
            onLoadNewState(args)
        }
    }

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModels()
    }

    @CallSuper
    override fun onDestroy() {
        fragSubscriptions.clear()
        super.onDestroy()
    }


}
