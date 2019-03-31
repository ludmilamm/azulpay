package br.com.azulpay.domain.model

import java.math.BigDecimal

data class User(val id: String,
                val name: String,
                val email: String,
                val phone: String,
                val image: String,
                val balance: BigDecimal)