package br.com.azulpay.data.remote.datasource

import br.com.azulpay.data.remote.model.LoginRequestRemoteModel
import br.com.azulpay.data.remote.model.LoginResponseRemoteModel
import br.com.azulpay.data.remote.model.RefreshTokenRequestRemoteModel
import br.com.azulpay.data.remote.model.RefreshTokenResponseRemoteModel
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRemoteDataSource {
    @POST("https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyBAzq8TMX9t8zkEwSX0WrjmewooC9TUv3I")
    fun login(@Body loginRequestRemoteModel: LoginRequestRemoteModel): Single<LoginResponseRemoteModel>

    @POST("https://securetoken.googleapis.com/v1/token?key=AIzaSyBAzq8TMX9t8zkEwSX0WrjmewooC9TUv3I")
    fun refreshToken(@Body refreshTokenRequestRemoteModel: RefreshTokenRequestRemoteModel): Single<RefreshTokenResponseRemoteModel>
}