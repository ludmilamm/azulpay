package br.com.azulpay.data.cache.model

data class UserCacheModel(val id: String,
                          val token: String,
                          val refreshToken: String,
                          val name: String,
                          val email: String,
                          val phone: String,
                          val image: String)