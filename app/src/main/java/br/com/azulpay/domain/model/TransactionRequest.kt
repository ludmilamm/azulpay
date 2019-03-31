package br.com.azulpay.domain.model

import java.math.BigDecimal

data class TransactionRequest(val value: BigDecimal,
                              val toUserId: String,
                              val toUserName: String,
                              val toUserPhone: String,
                              val toUserImage: String)