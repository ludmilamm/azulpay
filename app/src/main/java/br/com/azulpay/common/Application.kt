package br.com.azulpay.common

import android.app.Application
import android.os.Looper
import br.com.azulpay.common.di.ApplicationComponent
import br.com.azulpay.common.di.ApplicationModule
import br.com.azulpay.common.di.DaggerApplicationComponent
import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers

class Application : Application() {
    val component: ApplicationComponent? by lazy {
        DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()

        setupRx()
        RxPaperBook.init(this)
    }

    private fun setupRx() {
        //https://medium.com/@sweers/rxandroids-new-async-api-4ab5b3ad3e93
        //TL;DR melhora performance da UI
        val asyncMainThreadScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { asyncMainThreadScheduler }
    }
}