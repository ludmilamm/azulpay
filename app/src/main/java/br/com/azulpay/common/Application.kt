package br.com.azulpay.common

import android.app.Application
import br.com.azulpay.common.di.ApplicationComponent
import br.com.azulpay.common.di.ApplicationModule
import br.com.azulpay.common.di.DaggerApplicationComponent

class Application: Application() {
    val component: ApplicationComponent? by lazy {
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }
}