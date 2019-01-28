package ir.easazade.dailynotes.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ThreadUtils {
    companion object {
        fun runDelayed(delayMillis: Long, runnable: () -> Unit): Disposable {
            return Observable.just("nothing")
                .delay(delayMillis, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { runnable() }
        }
    }
}