package com.bytebyte6.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import com.bytebyte6.base.BaseFragment
import com.bytebyte6.base.BaseViewModelDelegate
import com.bytebyte6.base.EventObserver
import com.bytebyte6.logic.IpTvViewModel
import com.bytebyte6.logic.TAB_CATEGORY
import com.bytebyte6.logic.TAB_COUNTRY
import com.bytebyte6.logic.TAB_LANGUAGE
import com.bytebyte6.view.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.Hold
import org.koin.android.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    companion object {
        const val TAG = "HomeFragment"
    }

    private val viewModel: IpTvViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition=Hold()
    }

    override fun initBinding(view: View): FragmentHomeBinding {
        return FragmentHomeBinding.bind(view)
    }

    override fun initBaseViewModelDelegate(): BaseViewModelDelegate? = viewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw {  startPostponedEnterTransition() }

        binding?.apply {

            toolbar.setOnMenuItemClickListener {
                if (it.itemId==R.id.app_bar_share){
                    startActivity(
                        Intent(requireContext(),MainActivity::class.java)
                    )
                }
                true
            }

            viewPager.adapter = ViewPagerAdapter(this@HomeFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    TAB_COUNTRY -> tab.setText(R.string.home_country)
                    TAB_LANGUAGE -> tab.setText(R.string.home_language)
                    TAB_CATEGORY -> tab.setText(R.string.home_category)
                }
            }.attach()

            viewModel.apply {
                loading.observe(viewLifecycleOwner, EventObserver { loading ->
                    swipeRefreshLayout.isRefreshing = loading
                })

                listLiveData(TAB_CATEGORY)?.observe(viewLifecycleOwner, Observer {
                    tabLayout.getTabAt(TAB_CATEGORY)?.orCreateBadge?.number = (it as List<*>).size
                })

                listLiveData(TAB_COUNTRY)?.observe(viewLifecycleOwner, Observer {
                    tabLayout.getTabAt(TAB_COUNTRY)?.orCreateBadge?.number = (it as List<*>).size
                })

                listLiveData(TAB_LANGUAGE)?.observe(viewLifecycleOwner, Observer {
                    tabLayout.getTabAt(TAB_LANGUAGE)?.orCreateBadge?.number = (it as List<*>).size
                })
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }
            initSnack(viewPager)
        }
    }
}