package me.alexeygusev.testtask.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import me.alexeygusev.testtask.R
import me.alexeygusev.testtask.adapters.ItemClickedEvent
import me.alexeygusev.testtask.adapters.ItemsGridAdapter
import me.alexeygusev.testtask.api.models.Item
import me.alexeygusev.testtask.ui.DetailsActivity.Companion.EXTRA_DATA
import me.alexeygusev.testtask.ui.DetailsActivity.Companion.EXTRA_DESCRIPTION
import me.alexeygusev.testtask.ui.DetailsActivity.Companion.EXTRA_IMAGE_URL
import me.alexeygusev.testtask.ui.DetailsActivity.Companion.EXTRA_ITEM_ID
import me.alexeygusev.testtask.ui.DetailsActivity.Companion.EXTRA_TITLE
import me.alexeygusev.testtask.viewmodel.ItemsViewModel
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), LifecycleOwner {

    // TODO: Might implement list to grid selector by changing layout manager and adapter
    // TODO: Base class could be made non-abstract if we want to stick to Item data and then simply
    // TODO: switch adapters and managers for the RecycleView
//    private val listAdapter = ItemsListAdapter()
    private val gridAdapter = ItemsGridAdapter()
    private val disposable = CompositeDisposable()

    private lateinit var viewModel: ItemsViewModel
    private var itemsListUpdateObserver: Observer<ArrayList<Item>?> =
        Observer<ArrayList<Item>?> { items ->
            // TODO: Might implement list to grid selector by changing layout manager and adapter
//            listAdapter.submitList(items)
//            imagesGrid.layoutManager = LinearLayoutManager(this@MainActivity)
//            imagesGrid.adapter listAdapter

            gridAdapter.submitList(items)
            imagesGrid.layoutManager = GridLayoutManager(this, 2)
            imagesGrid.adapter = gridAdapter
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)
        viewModel.getItemsMutableLiveData().observe(this, itemsListUpdateObserver)

/*      TODO: Might implement list to grid selector by changing layout manager and adapter
        disposable.add(
            listAdapter.observe()
            .subscribe(
                { event ->
                    when (event) {
                        is ItemClickedEvent.ItemClicked -> {
                            Timber.d("CLICKED ON ${event.item}")
                            startActivity(
                                Intent(this, DetailsActivity::class.java).apply {
                                    putExtra(EXTRA_DATA, event.item)
                                }
                            )
                        }
                    }
                },
                Timber::e
            )
        )
*/

        disposable.add(
            gridAdapter.observe()
            .subscribe(
                { event ->
                    when (event) {
                        is ItemClickedEvent.ItemClicked -> {
                            Timber.d("CLICKED ON ${event.item}")
                            startActivity(
                                Intent(this, DetailsActivity::class.java).apply {
                                    putExtra(EXTRA_DATA, event.item)
                                }
                            )
                        }
                    }
                },
                Timber::e
            )
        )
    }

    override fun onDestroy() {
        disposable.dispose()
        viewModel.dispose()
        super.onDestroy()
    }
}
