package com.geeks4share.materix.network

import com.geeks4share.materix.network.model.LoginResponse
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by senthil-zt121 on 06/02/18.
 */
interface NetworkInterface {
    @POST("users/login")
    fun loginTokenValidation(@Body body: HashMap<String, String>) : Call<LoginResponse>

    object Factory {
        fun create(): NetworkInterface {

            val baseUrl = "http://sourceclone.com:9000/"


            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder().apply {
                readTimeout(2, TimeUnit.MINUTES)
                connectTimeout(2, TimeUnit.MINUTES)
                addInterceptor(logging)
            }

            okHttpClient.addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()

                val request = requestBuilder.build()
                chain.proceed(request)
            }

            val client = okHttpClient.build()
            return Retrofit.Builder().run {
                baseUrl(baseUrl)//No I18N
                addConverterFactory(GsonConverterFactory.create())
                client(client)
                build()
            }.create(NetworkInterface::class.java)
        }

    }
}