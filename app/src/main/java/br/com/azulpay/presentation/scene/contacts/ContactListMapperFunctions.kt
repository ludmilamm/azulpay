package br.com.azulpay.presentation.scene.contacts

import br.com.azulpay.domain.model.User

fun User.toDisplayModel() = ContactDisplayModel(id, name, phone, image)