package br.com.azulpay.presentation.common.model

import br.com.azulpay.R

open class ErrorDisplayModel(val message: Int)

open class ErrorDialogDisplayModel(
    message: Int,
    title: Int? = null,
    positiveLabel: Int? = R.string.ok,
    negativeLabel: Int? = null,
    cancelable: Boolean = true
) : DialogDisplayModel(message, title, positiveLabel, negativeLabel, cancelable) {

    object Unexpected : ErrorDialogDisplayModel(R.string.unexpected_error)
    object Internet : ErrorDialogDisplayModel(R.string.no_internet_error, cancelable = false)
}