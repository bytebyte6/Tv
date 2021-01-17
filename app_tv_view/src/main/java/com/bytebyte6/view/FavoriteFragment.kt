package com.bytebyte6.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bytebyte6.base_ui.BaseViewModel
import com.bytebyte6.base_ui.GridSpaceDecoration
import com.bytebyte6.base_ui.ListFragment
import com.bytebyte6.base_ui.databinding.FragmentListBinding
import com.bytebyte6.data.dao.TvDao
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : ListFragment() {

    companion object {
        const val TAG = "FavoriteFragment"
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupOnBackPressedDispatcherBackToHome()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disEnabledSwipeRefreshLayout()

        setupToolbarMenuMode(getString(R.string.nav_fav),"")

        val adapter = ImageAdapter(this)
        adapter.setOnItemClick { pos, _ ->
            showVideoActivity(adapter.currentList[pos].videoUrl)
        }
        adapter.setOnCurrentListChanged { _, currentList ->
            emptyBox.isVisible = currentList.isEmpty()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        recyclerView.addItemDecoration(GridSpaceDecoration())
        recyclerView.setHasFixedSize(true)

        viewModel.fav.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onLoadMore() {

    }

    override fun onRefresh() {

    }

    override fun initBinding(view: View): FragmentListBinding? {
        return null
    }
}

class FavoriteViewModel(tvDao: TvDao) : BaseViewModel() {
    val fav = tvDao.allFavorite()
}