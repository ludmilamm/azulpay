package br.com.azulpay.presentation.scene.history

import java.math.BigDecimal

data class TransactionDisplayModel(val toUserId: String,
                                   val toUserName: String,
                                   val toUserPhone: String,
                                   val toUserImage: String,
                                   val value: BigDecimal)