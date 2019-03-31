package br.com.azulpay.data.cache.model

import java.math.BigDecimal

data class UserCacheModel(val id: String,
                          val token: String,
                          val refreshToken: String,
                          val name: String,
                          val email: String,
                          val phone: String,
                          val image: String,
                          val balance: BigDecimal)