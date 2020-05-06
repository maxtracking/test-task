package me.alexeygusev.testtask.api

import io.reactivex.Maybe
import io.reactivex.Single
import me.alexeygusev.testtask.api.models.ApiResponse
import retrofit2.http.GET

/**
 * This interface would define API requests
 */
interface ApiService {

    @GET("download")
    fun downloadJson(): Single<ApiResponse>
}
