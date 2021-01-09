package com.bytebyte6.base_ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.bytebyte6.base_ui.databinding.FragmentListBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.progressindicator.LinearProgressIndicator

abstract class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var toolbar: MaterialToolbar
    lateinit var statusBar: View
    lateinit var linearProgressIndicator: LinearProgressIndicator
    lateinit var emptyBox: LottieAnimationView
    lateinit var appBarLayout: AppBarLayout
    var end = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearProgressIndicator = view.findViewById(R.id.linearProgressIndicator)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerview)
        appBarLayout = view.findViewById(R.id.appbar)
        toolbar = view.findViewById(R.id.toolbar)
        statusBar = view.findViewById(R.id.statusBar)
        emptyBox = view.findViewById(R.id.emptyBox)

        recyclerView.addOnScrollListener(object : BottomingListener() {
            override fun onBottom() {
                if (end) {
                    return
                }
                if (swipeRefreshLayout.isRefreshing){
                    return
                }
                if (linearProgressIndicator.isVisible){
                    return
                }
                onLoadMore()
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            end = false
            onRefresh()
        }
    }

    fun disEnabledSwipeRefreshLayout() {
        swipeRefreshLayout.isEnabled = false
    }

    fun showSwipeRefresh() {
        swipeRefreshLayout.isRefreshing = true
    }

    fun hideSwipeRefresh() {
        swipeRefreshLayout.postDelayed({
            swipeRefreshLayout.isRefreshing = false
        }, 1000)
    }

    fun showProgress() {
        if (!swipeRefreshLayout.isRefreshing) {
            linearProgressIndicator.show()
        }
    }

    fun hideProgress() {
        linearProgressIndicator.hide()
    }

    abstract fun onLoadMore()
    abstract fun onRefresh()
}