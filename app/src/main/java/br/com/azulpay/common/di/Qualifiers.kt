package br.com.azulpay.common.di

import javax.inject.Qualifier

@Qualifier
annotation class MainScheduler

@Qualifier
annotation class BackgroundScheduler

@Qualifier
annotation class ApplicationContext