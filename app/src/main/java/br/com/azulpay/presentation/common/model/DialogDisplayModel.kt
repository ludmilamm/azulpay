package br.com.azulpay.presentation.common.model

import androidx.annotation.StringRes
import br.com.azulpay.R

open class DialogDisplayModel(
    @StringRes val message: Int,
    @StringRes val title: Int? = null,
    @StringRes val positiveLabel: Int? = R.string.ok,
    @StringRes val negativeLabel: Int? = null,
    val cancelable: Boolean = true
)

