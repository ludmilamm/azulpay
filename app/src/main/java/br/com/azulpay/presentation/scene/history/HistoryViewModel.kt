package br.com.azulpay.presentation.scene.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.azulpay.domain.usecase.GetSentTransactions
import br.com.azulpay.presentation.common.BaseViewModel
import br.com.azulpay.presentation.common.event.*
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class HistoryViewModel @Inject constructor(private val getSentTransactions: GetSentTransactions) : BaseViewModel() {

    private val transactionsEvent = MutableLiveData<StateEvent<List<TransactionDisplayModel>>>()
    val transactionsLiveData: LiveData<StateEvent<List<TransactionDisplayModel>>> = transactionsEvent

    init {
        getTransactions()
    }

    private fun getTransactions() {
        baseEventsLiveData.postLoading()
        getSentTransactions.getSingle(Unit)
                .map { it.map { it.toDisplayModel() } }
                .doFinally { baseEventsLiveData.postDismissLoading() }
                .subscribe({
                    transactionsEvent.postSuccess(it)
                }, {
                    transactionsEvent.postErrorDialog(mapErrorToDisplayModel(it))
                }).addTo(disposables)
    }
}
