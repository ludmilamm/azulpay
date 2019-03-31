package br.com.azulpay.presentation.scene.contacts

import br.com.azulpay.presentation.common.model.ContactDisplayModel
import java.math.BigDecimal

data class TransactionDisplayModel(val value: BigDecimal,
                                   val contact: ContactDisplayModel)