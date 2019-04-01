package br.com.azulpay.presentation.scene.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.azulpay.common.EmptyFieldException
import br.com.azulpay.domain.usecase.GetContacts
import br.com.azulpay.domain.usecase.PostTransaction
import br.com.azulpay.domain.usecase.ValidateValueToSend
import br.com.azulpay.presentation.common.BaseViewModel
import br.com.azulpay.presentation.common.event.*
import br.com.azulpay.presentation.common.model.ContactDisplayModel
import com.hadilq.liveevent.LiveEvent
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ContactListViewModel @Inject constructor(private val getContacts: GetContacts,
                                               private val postTransaction: PostTransaction,
                                               private val validateValueToSend: ValidateValueToSend) : BaseViewModel() {

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
                    contactEvent.postErrorDialog(mapErrorToDisplayModel(it))
                }).addTo(disposables)
    }

    fun postTransaction(transactionDisplayModel: TransactionDisplayModel) {
        baseEventsLiveData.postLoading()
        validateValueToSend.getCompletable(transactionDisplayModel.value)
                .doOnComplete { transactionEvent.value = SingleEvent.Loading() }
                .doOnError {
                    transactionEvent.postError(when (it) {
                        is EmptyFieldException -> EmptyValueErrorDisplayModel()
                        else -> InvalidValueErrorDisplayModel()
                    })
                }.andThen(postTransaction.getCompletable(transactionDisplayModel.toDomainModel())
                        .doOnComplete { transactionEvent.postSuccess(Unit) }
                        .doOnError { baseEventsLiveData.postErrorDialog(mapErrorToDisplayModel(it)) })
                .onErrorComplete()
                .doFinally { baseEventsLiveData.postDismissLoading() }
                .subscribe({}, {}).addTo(disposables)
    }


}
