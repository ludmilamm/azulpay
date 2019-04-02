package br.com.azulpay.presentation.scene.contacts

import br.com.azulpay.R
import br.com.azulpay.presentation.common.model.ContactDisplayModel
import br.com.azulpay.presentation.common.model.ErrorDialogDisplayModel
import br.com.azulpay.presentation.common.model.ErrorDisplayModel
import java.math.BigDecimal

data class TransactionDisplayModel(val value: BigDecimal,
                                   val contact: ContactDisplayModel)

data class EmptyValueErrorDisplayModel(val emptyMessage: Int = R.string.empty_value): ErrorDisplayModel(emptyMessage)

data class InvalidValueErrorDisplayModel(val emptyMessage: Int = R.string.invalid_value): ErrorDisplayModel(emptyMessage)