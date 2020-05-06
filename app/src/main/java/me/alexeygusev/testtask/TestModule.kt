package me.alexeygusev.testtask

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.alexeygusev.testtask.api.ApiService
import me.alexeygusev.testtask.data.DownloadJsonUseCase
import me.alexeygusev.testtask.data.DownloadJsonUseCaseImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin Module, would be at different location in the more complex project.
 */
val testModule = module {
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
    single {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logInterceptor)
        }
        builder.build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://cloud.nousdigital.net/s/Njedq4WpjWz4KKk/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }
    factory<DownloadJsonUseCase> { DownloadJsonUseCaseImpl(get()) }
}
