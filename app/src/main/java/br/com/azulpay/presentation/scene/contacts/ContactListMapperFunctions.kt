package br.com.azulpay.presentation.scene.contacts

import br.com.azulpay.domain.model.TransactionRequest
import br.com.azulpay.domain.model.User
import br.com.azulpay.presentation.common.model.ContactDisplayModel

fun User.toDisplayModel() = ContactDisplayModel(id, name, phone, image)

fun TransactionDisplayModel.toDomainModel() = TransactionRequest(value, contact.id, contact.name, contact.phone, contact.image)