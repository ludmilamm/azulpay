package br.com.azulpay.data.remote

import br.com.azulpay.data.cache.model.UserCacheModel
import br.com.azulpay.data.remote.model.UserRemoteModel
import br.com.azulpay.domain.model.User
import java.math.BigDecimal

fun UserRemoteModel.toCacheModel(token: String, refreshToken: String) = UserCacheModel(id.string, token, refreshToken, name.string, email.string, phone.string, image.string, BigDecimal.valueOf(balance.double))

fun UserRemoteModel.toDomainModel() = User(id.string, name.string, email.string, phone.string, image.string, BigDecimal.valueOf(balance.double))