package me.alexeygusev.testtask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
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
 * An adapter to handle items based on downloaded json file.
 */
//class ItemsListAdapter : ListAdapter<Item, ItemViewHolder>(DIFF_CALLBACK) {
class ItemsListAdapter : BaseListAdapter<Item>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }

//    private val clickPublisher = PublishSubject.create<ItemClickedEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.containerView.run {
            Glide.with(context)
                .load(item.imageUrl)
                .placeholder(R.drawable.ic_block_black_24dp)
                .into(image)
            title.text = item.title
            description.text = item.description

            this.clicks().map { ItemClickedEvent.ItemClicked(item) }.subscribe(clickPublisher)
        }
    }

//    fun observe(): Observable<ItemClickedEvent> = clickPublisher.hide()
}


