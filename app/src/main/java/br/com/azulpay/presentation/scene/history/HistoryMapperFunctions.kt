package br.com.azulpay.presentation.scene.history

import br.com.azulpay.domain.model.Transaction

fun Transaction.toDisplayModel() = TransactionDisplayModel(to.id, to.name, to.phone, to.image, value)