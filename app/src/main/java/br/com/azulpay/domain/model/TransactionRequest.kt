package br.com.azulpay.domain.model

import java.math.BigDecimal

data class TransactionRequest(val value: BigDecimal,
                              val id: String,
                              val name: String,
                              val phone: String)