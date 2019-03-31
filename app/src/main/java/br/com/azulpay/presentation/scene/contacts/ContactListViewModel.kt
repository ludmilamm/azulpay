package br.com.azulpay.presentation.scene.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.azulpay.domain.usecase.GetContacts
import br.com.azulpay.presentation.common.BaseViewModel
import br.com.azulpay.presentation.common.event.*
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class ContactListViewModel @Inject constructor(private val getContacts: GetContacts) : BaseViewModel() {

    private val contactsLiveDataEvent = MutableLiveData<StateEvent<List<ContactDisplayModel>>>()
    val contactsLiveData: LiveData<StateEvent<List<ContactDisplayModel>>> = contactsLiveDataEvent

    init {
        getContacts()
    }

    private fun getContacts() {
        baseEventsLiveData.postLoading()
        getContacts.getSingle(Unit)
                .map { it.map { it.toDisplayModel() } }
                .doFinally { baseEventsLiveData.postDismissLoading() }
                .subscribe({
                    contactsLiveDataEvent.postSuccess(it)
                }, {
                    contactsLiveDataEvent.postError(mapErrorToDisplayModel(it))
                }).addTo(disposables)
    }
}
