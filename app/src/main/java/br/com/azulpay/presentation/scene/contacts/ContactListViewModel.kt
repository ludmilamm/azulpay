package br.com.azulpay.presentation.scene.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.azulpay.domain.usecase.GetContacts
import br.com.azulpay.domain.usecase.PostTransaction
import br.com.azulpay.presentation.common.BaseViewModel
import br.com.azulpay.presentation.common.event.*
import br.com.azulpay.presentation.common.model.ContactDisplayModel
import com.hadilq.liveevent.LiveEvent
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ContactListViewModel @Inject constructor(private val getContacts: GetContacts,
                                               private val postTransaction: PostTransaction) : BaseViewModel() {

    private val contactEvent = MutableLiveData<StateEvent<List<ContactDisplayModel>>>()
    val contactsLiveData: LiveData<StateEvent<List<ContactDisplayModel>>> = contactEvent

    private val transactionEvent = LiveEvent<SingleEvent<Unit>>()
    val transactionLiveData: LiveData<SingleEvent<Unit>> = transactionEvent

    init {
        getContacts()
    }

    private fun getContacts() {
        baseEventsLiveData.postLoading()
        getContacts.getSingle(Unit)
                .map { it.map { it.toDisplayModel() } }
                .doFinally { baseEventsLiveData.postDismissLoading() }
                .subscribe({
                    contactEvent.postSuccess(it)
                }, {
                    contactEvent.postError(mapErrorToDisplayModel(it))
                }).addTo(disposables)
    }

    fun postTransaction(transactionDisplayModel: TransactionDisplayModel) {
        baseEventsLiveData.postLoading()
        postTransaction.getCompletable(transactionDisplayModel.toDomainModel())
                .doFinally { baseEventsLiveData.postDismissLoading() }
                .subscribe({
                    transactionEvent.postSuccess(Unit)
                },{
                    baseEventsLiveData.postError(mapErrorToDisplayModel(it))
                }).addTo(disposables)
    }
}
