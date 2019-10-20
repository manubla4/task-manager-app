package com.manubla.taskmanager.controller


import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.manubla.taskmanager.App
import com.manubla.taskmanager.controller.adapter.ZonedDateTimeAdapter
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

object RetrofitController {
    var accessToken: String? = null

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://android-todos-api.herokuapp.com/")
        .addConverterFactory(gsonConverterFactory)
        .client(httpClient)
        .build()

    private val gsonConverterFactory
        get() = GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(ZonedDateTime::class.java, ZonedDateTimeAdapter())
                .create()
        )

    private val httpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(App.application))
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())

                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED && accessToken != null)
                    App.goToLoginScreen()

                response
            }
            .addInterceptor { chain ->
                if (accessToken != null) {
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", accessToken ?: "")
                        .build()

                    chain.proceed(request)
                } else {
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()

                    chain.proceed(request)
                }
            }
            .build()
}