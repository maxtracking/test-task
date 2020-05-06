package me.alexeygusev.testtask.data

import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.alexeygusev.testtask.api.ApiService
import me.alexeygusev.testtask.api.models.ApiResponse
import me.alexeygusev.testtask.api.models.Item

/**
 * Define an interface for UC in order to be able to unit-test it.
 */
interface DownloadJsonUseCase {
    fun execute(): Maybe<List<Item>>
}

/**
 * For better testability, we might also inject a scheduler and then use TestScheduler in UCs
 */
class DownloadJsonUseCaseImpl(private val apiService: ApiService) : DownloadJsonUseCase {

    override fun execute(): Maybe<List<Item>> =
        apiService
            .downloadJson()
            .doOnSuccess {
                println("SUCCESS from downloadJson()")
            }
            .doOnError {
                println("ERROR from downloadJson()")
            }
            .filter {
                it.items.isNotEmpty()
            }
            .switchIfEmpty(Single.error(IllegalStateException("Items list cannot be empty")))
            .flatMapMaybe {
                Maybe.just(it.items)
            }
}
