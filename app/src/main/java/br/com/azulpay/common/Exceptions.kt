package br.com.azulpay.common

sealed class GenericException : RuntimeException()
class UnexpectedException : GenericException()
class NoInternetException : GenericException()
class InvalidFieldException: GenericException()
class EmptyFieldException: GenericException()