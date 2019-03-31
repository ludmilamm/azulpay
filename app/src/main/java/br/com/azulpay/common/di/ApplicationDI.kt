package br.com.azulpay.common.di

import android.content.Context
import android.util.Log
import br.com.azulpay.data.cache.datasource.UserCacheDataSource
import br.com.azulpay.data.remote.datasource.LoginRemoteDataSource
import br.com.azulpay.data.remote.datasource.TransactionRemoteDataSource
import br.com.azulpay.data.remote.datasource.UserRemoteDataSource
import br.com.azulpay.data.remote.infrastructure.ErrorHandlingRxCallAdapterFactory
import br.com.azulpay.data.remote.infrastructure.GeneralInterceptor
import br.com.azulpay.data.repository.TransactionRepository
import br.com.azulpay.data.repository.UserRepository
import br.com.azulpay.domain.datarepository.TransactionDataRepository
import br.com.azulpay.domain.datarepository.UserDataRepository
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun okHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })

    @Provides
    @Singleton
    @GeneralRetrofit
    fun provideOkHttpClient(generalInterceptor: GeneralInterceptor, okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient =
            okHttpClientBuilder.addInterceptor(generalInterceptor).build()

    @Provides
    @Singleton
    @GeneralRetrofit
    fun provideRetrofit(@GeneralRetrofit okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(ErrorHandlingRxCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://firestore.googleapis.com/v1beta1/projects/azulpay-713d1/databases/(default)/documents/")
            .build()

    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(okHttpClientBuilder: OkHttpClient.Builder): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(ErrorHandlingRxCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .baseUrl("https://www.googleapis.com/identitytoolkit/v3/relyingparty/")
            .build()

    @Provides
    @Singleton
    fun provideLoginRemoteDataSource(@AuthRetrofit retrofit: Retrofit) = retrofit.create(LoginRemoteDataSource::class.java)

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(@GeneralRetrofit retrofit: Retrofit) = retrofit.create(UserRemoteDataSource::class.java)

    @Provides
    @Singleton
    fun provideTransactionRemoteDataSource(@GeneralRetrofit retrofit: Retrofit) = retrofit.create(TransactionRemoteDataSource::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(userRepository: UserRepository): UserDataRepository = userRepository

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionRepository: TransactionRepository): TransactionDataRepository = transactionRepository
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

    // cache data source
    fun userCacheDataSource(): UserCacheDataSource

    // fragments
    fun inject(fragment: HomeFragment)
    fun inject(fragment: ContactListFragment)
    fun inject(fragment: HistoryFragment)
}