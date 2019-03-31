package br.com.azulpay.data.remote.datasource

import br.com.azulpay.data.remote.model.TransactionRequestRemoteModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionRemoteDataSource {
    @POST("transactions")
    fun postTransaction(@Body transaction: TransactionRequestRemoteModel): Single<ResponseBody>
}