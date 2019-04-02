package br.com.azulpay.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.azulpay.common.DisposableHolder
import br.com.azulpay.common.DisposableHolderDelegate
import br.com.azulpay.common.NoInternetException
import br.com.azulpay.presentation.common.event.SingleEvent
import br.com.azulpay.presentation.common.model.ErrorDialogDisplayModel
import com.hadilq.liveevent.LiveEvent

open class BaseViewModel : ViewModel(), DisposableHolder by DisposableHolderDelegate() {

    // LiveData to emmit commons events to BaseFragment
    val baseEventsLiveData: LiveEvent<SingleEvent<*>> = LiveEvent()

    fun getBaseEventsLiveData(): LiveData<SingleEvent<*>> = baseEventsLiveData

    override fun onCleared() {
        super.onCleared()
        disposeAll()
    }

    fun mapErrorDialogDisplayModel(error: Throwable): ErrorDialogDisplayModel {
        return when(error) {
            is NoInternetException -> ErrorDialogDisplayModel.Internet
            else -> ErrorDialogDisplayModel.Unexpected
        }
    }
}