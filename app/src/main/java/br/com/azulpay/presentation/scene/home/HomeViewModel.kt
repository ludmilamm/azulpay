package br.com.azulpay.presentation.scene.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.azulpay.domain.usecase.GetAuthenticatedUser
import br.com.azulpay.presentation.common.BaseViewModel
import br.com.azulpay.presentation.common.event.*
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getAuthenticatedUser: GetAuthenticatedUser) : BaseViewModel() {

    private val userEvent = MutableLiveData<StateEvent<UserDisplayModel>>()
    val userLiveData: LiveData<StateEvent<UserDisplayModel>> = userEvent

    init {
        getUser()
    }

    private fun getUser() {
        baseEventsLiveData.postLoading()
        getAuthenticatedUser.getSingle(Unit)
                .map { it.toDisplayModel() }
                .doFinally { baseEventsLiveData.postDismissLoading() }
                .subscribe({
                    userEvent.postSuccess(it)
                }, {
                    baseEventsLiveData.postError(mapErrorToDisplayModel(it))
                }).addTo(disposables)
    }
}
