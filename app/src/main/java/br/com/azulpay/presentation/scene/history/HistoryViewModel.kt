package br.com.azulpay.presentation.scene.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.azulpay.domain.usecase.GetSentTransactions
import br.com.azulpay.presentation.common.BaseViewModel
import br.com.azulpay.presentation.common.event.*
import com.github.mikephil.charting.data.BarEntry
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class HistoryViewModel @Inject constructor(private val getSentTransactions: GetSentTransactions) : BaseViewModel() {

    private val transactionsEvent = MutableLiveData<StateEvent<List<TransactionDisplayModel>>>()
    val transactionsLiveData: LiveData<StateEvent<List<TransactionDisplayModel>>> = transactionsEvent

    private val transactionsGroupedByUserEvent = MutableLiveData<StateEvent<List<BarEntry>>>()
    val transactionsGroupedByUserLiveData: LiveData<StateEvent<List<BarEntry>>> = transactionsGroupedByUserEvent

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
                    transactionsGroupedByUserEvent.postSuccess(getBarEntries(it))
                }, {
                    transactionsEvent.postError(mapErrorToDisplayModel(it))
                }).addTo(disposables)
    }

    private fun getBarEntries(transactions:List<TransactionDisplayModel>): List<BarEntry> {
        var float = 0f
        return transactions.groupBy { it.toUserId }
                .map { BarEntry(float++, it.value.map { it.value }.reduce { acc, decimal -> acc + decimal }.toFloat()) }
    }
}
