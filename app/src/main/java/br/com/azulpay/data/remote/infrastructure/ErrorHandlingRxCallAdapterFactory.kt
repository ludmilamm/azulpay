package br.com.azulpay.data.remote.infrastructure

import br.com.azulpay.common.NoInternetException
import br.com.azulpay.common.UnexpectedException
import io.reactivex.Single
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.UnknownHostException

class ErrorHandlingRxCallAdapterFactory : CallAdapter.Factory() {
    private val rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type?,
                     annotations: Array<out Annotation>?,
                     retrofit: Retrofit?): CallAdapter<in Any, out Any>? {

        return RxCallAdapterWrapper(
            rxJavaCallAdapterFactory.get(returnType!!, annotations!!, retrofit!!) as CallAdapter<in Any, out Any>
        )
    }

    private class RxCallAdapterWrapper internal constructor(private val wrapped: CallAdapter<in Any, out Any>) :
        CallAdapter<Any, Single<Any>> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<Any>): Single<Any> {
            return (wrapped.adapt(call) as Single<Any>).onErrorResumeNext { Single.error(toGenericRemoteApiException(it)) }
        }

        fun toGenericRemoteApiException(throwable: Throwable): Throwable {
            if ((throwable is HttpException && throwable.code() == HttpURLConnection.HTTP_INTERNAL_ERROR) || throwable is SocketException) {
                return UnexpectedException()
            } else if (throwable is UnknownHostException) {
                return NoInternetException()
            }

            return throwable
        }
    }
}