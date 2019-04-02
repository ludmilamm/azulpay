package br.com.azulpay.data.cache

import br.com.azulpay.data.cache.model.UserCacheModel
import br.com.azulpay.domain.model.User

fun UserCacheModel.toDomainModel(): User = User(id, name, email, phone, image)