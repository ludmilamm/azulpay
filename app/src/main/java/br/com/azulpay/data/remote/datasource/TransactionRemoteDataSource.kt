package br.com.azulpay.data.remote.datasource

import br.com.azulpay.data.remote.model.DocumentsRemoteModel
import br.com.azulpay.data.remote.model.TransactionRequestRemoteModel
import br.com.azulpay.data.remote.model.TransactionResponseRemoteModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TransactionRemoteDataSource {
    @POST("transactions")
    fun postTransaction(@Body transaction: TransactionRequestRemoteModel): Single<ResponseBody>

    @GET("transactions")
    fun getTransactions(): Single<DocumentsRemoteModel<TransactionResponseRemoteModel>>
}