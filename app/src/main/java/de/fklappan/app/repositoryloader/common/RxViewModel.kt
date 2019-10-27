package de.fklappan.app.repositoryloader.common

import android.util.Log
import androidx.lifecycle.ViewModel
import de.fklappan.app.repositoryloader.domain.LOG_TAG
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base ViewModel to provide an Rx CompositeDisposable which is used for UseCase classes
 */
open class RxViewModel : ViewModel() {
    private val disposables = CompositeDisposable()

    override fun onCleared() {
        Log.d(LOG_TAG, "onCleared")
        super.onCleared()
        disposables.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        Log.d(LOG_TAG, "addDisposable")
        disposables.add(disposable)
    }

}