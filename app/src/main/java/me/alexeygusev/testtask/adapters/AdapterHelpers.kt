package me.alexeygusev.testtask.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import me.alexeygusev.testtask.api.models.Item

class ItemViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer

sealed class ItemClickedEvent(open val item: Item) {
    data class ItemClicked(override val item: Item) : ItemClickedEvent(item)
    // here might be more sophisticated events or clicks on various parts of the UI
}
