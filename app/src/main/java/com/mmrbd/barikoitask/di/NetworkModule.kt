package com.mmrbd.barikoitask.di

import com.mmrbd.barikoitask.BuildConfig
import com.mmrbd.barikoitask.data.remote.BariKoiApi
import com.mmrbd.barikoitask.utils.network.NetworkFailureMessage
import com.mmrbd.barikoitask.utils.network.NetworkFailureMessageImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideOkkHttpClint() }
    factory { provideRetrofit(get()) }
    factory { provideBariKoiApi(get()) }
    single<NetworkFailureMessage> { NetworkFailureMessageImpl(androidContext()) }
}


fun provideOkkHttpClint(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BARIKOI_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideBariKoiApi(retrofit: Retrofit): BariKoiApi {
    return retrofit.create(BariKoiApi::class.java)
}
