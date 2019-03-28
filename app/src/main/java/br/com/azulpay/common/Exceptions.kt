package br.com.azulpay.common

sealed class GenericException : RuntimeException()
class UnexpectedException : GenericException()
class NoInternetException : GenericException()