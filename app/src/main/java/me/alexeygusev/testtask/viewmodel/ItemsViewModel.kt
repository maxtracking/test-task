package me.alexeygusev.testtask.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.alexeygusev.testtask.api.models.Item
import me.alexeygusev.testtask.data.DownloadJsonUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ItemsViewModel : ViewModel(), KoinComponent, LifecycleObserver {

    private val itemsMutableLiveData: MutableLiveData<ArrayList<Item>?> = MutableLiveData()
    private val itemsArrayList: ArrayList<Item> = arrayListOf()
    private val downloadJsonUseCase: DownloadJsonUseCase by inject()
    private val disposable = CompositeDisposable()

    fun getItemsMutableLiveData() = itemsMutableLiveData

    fun dispose() {
        disposable.dispose()
        println("VIEWMODEL CALLED DISPOSE")
    }

    private fun initModel() {
        populateList()
        itemsMutableLiveData.value = itemsArrayList
    }

    private fun populateList() {
        disposable.add(
            downloadJsonUseCase
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        itemsArrayList.clear()
                        itemsArrayList.addAll(data)
                        itemsMutableLiveData.value = itemsArrayList
                    },
                    Timber::e
                )
        )
    }

    init {
        initModel()
    }
}
