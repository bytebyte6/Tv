package com.bytebyte6.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bytebyte6.base.mvi.emit
import com.bytebyte6.data.AppDatabase
import com.bytebyte6.data.dataModule
import com.bytebyte6.data.roomMemoryModule
import com.bytebyte6.usecase.useCaseModule
import com.bytebyte6.view.home.HomeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import java.util.concurrent.CountDownLatch


@RunWith(AndroidJUnit4::class)
class HomeViewModelTest : AutoCloseKoinTest() {

    private val viewModel by inject<HomeViewModel>()
    private val db: AppDatabase by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start() {
        stopKoin()
        startKoin {
            modules(roomMemoryModule, dataModule, useCaseModule, viewModule)
        }
//        RxJavaPlugins.reset()
//        RxJavaPlugins.setIoSchedulerHandler(object : Function<Scheduler, Scheduler> {
//            @Throws(Exception::class)
//            override fun apply(scheduler: Scheduler): Scheduler {
//                return Schedulers.trampoline()
//            }
//        })
    }

    @Test
    fun test_searchLogo(){
        viewModel.searchLogo(0)
    }

    @Test
    fun test_category() {
        val countDownLatch = CountDownLatch(1)
        viewModel.category.observeForever {
            if(it.isNotEmpty()){
                countDownLatch.countDown()
            }
        }
        viewModel.refresh()
        countDownLatch.await()
        assert(viewModel.category.value != null)
        assert(viewModel.category.value!!.isNotEmpty())
    }

    @Test
    fun test_refresh() {
        val countDownLatch = CountDownLatch(1)
        var loadingCount = 0
        var successCount = 0
        var errorCount = 0
        viewModel.tvRefresh.observeForever {
            it.emit(
                {
                    successCount++
                    countDownLatch.countDown()
                }, {
                    errorCount++
                    countDownLatch.countDown()
                }, {
                    loadingCount++
                }
            )
        }
        viewModel.refresh()
        countDownLatch.await()
        assert(loadingCount == 1)
        assert(successCount == 1)
        assert(errorCount == 0)
    }
}