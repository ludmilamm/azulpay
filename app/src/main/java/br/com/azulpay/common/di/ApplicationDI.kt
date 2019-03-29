package br.com.azulpay.common.di

import android.content.Context
import android.util.Log
import br.com.azulpay.data.remote.infrastructure.ErrorHandlingRxCallAdapterFactory
import br.com.azulpay.domain.utility.Logger
import br.com.azulpay.presentation.di.ViewModelModule
import br.com.azulpay.presentation.scene.contacts.ContactListFragment
import br.com.azulpay.presentation.scene.history.HistoryFragment
import br.com.azulpay.presentation.scene.home.HomeFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule(private val context: Context) {

    @Provides
    @BackgroundScheduler
    fun provideBackgroundScheduler() = Schedulers.io()

    @Provides
    @Singleton
    @MainScheduler
    fun provideMainScheduler() = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideLogger() = object : Logger {
        override fun log(t: Throwable) {
            Log.e("Exception: ", "", t)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(ErrorHandlingRxCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://firestore.googleapis.com/v1beta1/projects/azulpay-713d1/databases/(default)/").build()
}

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @BackgroundScheduler
    fun backgroundScheduler(): Scheduler

    @MainScheduler
    fun mainScheduler(): Scheduler

    @ApplicationContext
    fun context(): Context

    fun logger(): Logger

    fun retrofit(): Retrofit

    fun inject(fragment: HomeFragment)
    fun inject(fragment: ContactListFragment)
    fun inject(fragment: HistoryFragment)
}