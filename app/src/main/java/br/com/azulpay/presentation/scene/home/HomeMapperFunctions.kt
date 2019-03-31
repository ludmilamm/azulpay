package br.com.azulpay.presentation.scene.home

import br.com.azulpay.domain.model.User

fun User.toDisplayModel() = UserDisplayModel(image, name, email)