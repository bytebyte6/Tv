package com.bytebyte6.view.home

import android.view.View
import androidx.lifecycle.Observer
import com.bytebyte6.base.*
import com.bytebyte6.base.mvi.isError
import com.bytebyte6.base.mvi.isLoading
import com.bytebyte6.base_ui.BaseFragment
import com.bytebyte6.base_ui.Message
import com.bytebyte6.base_ui.showSnack
import com.bytebyte6.view.*
import com.bytebyte6.view.R
import com.bytebyte6.view.databinding.FragmentHomeBinding
import com.bytebyte6.view.search.SearchFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    companion object {
        const val TAG = "HomeFragment"
    }

    private val viewModel: HomeViewModel by viewModel()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        exitTransition = Hold()
//    }

    override fun initBinding(view: View): FragmentHomeBinding {
        return FragmentHomeBinding.bind(view).apply {

//            postponeEnterTransition()
//
//            view.doOnPreDraw { startPostponedEnterTransition() }

            toolbar.transitionName = "searchShare"

            setupToolbar(requireActivity())

            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.app_bar_share) {
                    //share intent
                } else {
                    replaceWithShareElement(
                        SearchFragment.newInstance(toolbar.transitionName),
                        SearchFragment.TAG,
                        toolbar
                    )
                }
                true
            }

            viewPager.adapter =
                TabAdapter(this@HomeFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    TAB_COUNTRY -> tab.setText(R.string.home_country)
                    TAB_LANGUAGE -> tab.setText(R.string.home_language)
                    TAB_CATEGORY -> tab.setText(R.string.home_category)
                }
            }.attach()

            viewModel.apply {

                category.observe(viewLifecycleOwner, Observer {
                    tabLayout.getTabAt(TAB_CATEGORY)?.orCreateBadge?.number = it.size
                })

                cs.observe(viewLifecycleOwner, Observer {
                    tabLayout.getTabAt(TAB_COUNTRY)?.orCreateBadge?.number = it.size
                })

                lang.observe(viewLifecycleOwner, Observer {
                    tabLayout.getTabAt(TAB_LANGUAGE)?.orCreateBadge?.number = it.size
                })

                tvRefresh.observe(viewLifecycleOwner, EventObserver {
                    swipeRefreshLayout.isRefreshing = it.isLoading()
                    it.isError()?.apply {
                        showSnack(
                            view,
                            Message(
                                id = ErrorUtils.getMessage(
                                    this
                                )
                            )
                        )
                    }
                })
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }

        }
    }


}