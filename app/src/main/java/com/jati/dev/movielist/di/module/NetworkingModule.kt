package com.jati.dev.movielist.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jati.dev.movielist.BuildConfig
import com.jati.dev.movielist.network.ApiManager
import com.jati.dev.movielist.network.ApiServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by jati on 05/05/18
 */

@Module
class NetworkingModule {
    private val REQUEST_TIMEOUT = 30
    private val BASE_URL = "http://www.omdbapi.com/"

    @Provides
    @Singleton
    fun providesGson() = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()

    @Provides
    @Singleton
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

    @Provides
    @Singleton
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Named("omdbEndPoint")
    @Singleton
    fun providesRetrofitOmdb(okHttpClient: OkHttpClient, gson: Gson) = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesApiServices(@Named("omdbEndPoint") retrofit: Retrofit) = retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun providesApiManager(services: ApiServices) = ApiManager(services)
}