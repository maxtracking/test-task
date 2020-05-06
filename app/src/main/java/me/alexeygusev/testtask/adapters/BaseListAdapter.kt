package me.alexeygusev.testtask.adapters

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.list_item.view.*
import me.alexeygusev.testtask.R
import me.alexeygusev.testtask.api.models.Item

/**
 * A base class for list and grid adapters
 */
abstract class BaseListAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, ItemViewHolder>(diffCallback) {

    protected val clickPublisher = PublishSubject.create<ItemClickedEvent>()

    fun observe(): Observable<ItemClickedEvent> = clickPublisher.hide()
}
