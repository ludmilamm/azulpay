package br.com.azulpay.domain.model

import java.math.BigDecimal

data class Transaction(val value: BigDecimal,
                       val to: User,
                       val from: User,
                       val date: Long) {

    data class User(val id: String,
                    val name: String,
                    val phone: String,
                    val image: String)
}