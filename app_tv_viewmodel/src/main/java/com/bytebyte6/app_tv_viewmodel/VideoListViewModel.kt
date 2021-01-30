package com.bytebyte6.app_tv_viewmodel

import androidx.lifecycle.LiveData
import com.bytebyte6.base.*
import com.bytebyte6.data.dao.TvFtsDao
import com.bytebyte6.data.entity.TvFts
import com.bytebyte6.usecase.SearchParam
import com.bytebyte6.usecase.TvLogoSearchUseCase
import com.bytebyte6.usecase.UpdateTvParam
import com.bytebyte6.usecase.UpdateTvUseCase

class VideoListViewModel(
    private val tvFtsDao: TvFtsDao,
    private val tvLogoSearchUseCase: TvLogoSearchUseCase,
    private val updateTvUseCase: UpdateTvUseCase
) : BaseViewModel() {

    private val pagingHelper: PagingHelper<TvFts>

    private var key: String = ""

    val tvs: LiveData<Result<List<TvFts>>>

    private val favorite = updateTvUseCase.result()

    private val favoriteObserver: (Result<UpdateTvParam>) -> Unit

    private val searchObserver: (Result<SearchParam>) -> Unit

    init {
        pagingHelper = object : PagingHelper<TvFts>(50) {
            override fun count(): Int = tvFtsDao.getCount(getKey())
            override fun paging(offset: Int, pageSize: Int): List<TvFts> =
                tvFtsDao.paging(offset, getKey(), pageSize)
        }
        tvs = pagingHelper.result()
        favoriteObserver = { result ->
            result.emitIfNotHandled(success = {
                val data = pagingHelper.getList()[it.data.pos]
                data.favorite = it.data.tv.favorite
                pagingHelper.dataHasBeenChanged()
            })
        }
        favorite.observeForever(favoriteObserver)
        searchObserver = { result ->
            result.emitIfNotHandled(success = {
                val data = pagingHelper.getList()[it.data.pos]
                data.logo = it.data.logo
                pagingHelper.dataHasBeenChanged()
            })
        }
        tvLogoSearchUseCase.result().observeForever(searchObserver)
    }

    override fun onCleared() {
        favorite.removeObserver(favoriteObserver)
        tvLogoSearchUseCase.result().removeObserver(searchObserver)
        super.onCleared()
    }

    fun setKey(key: String) {
        this.key = key
    }

    fun getKey() = key

    fun count(item: String): LiveData<Int> = tvFtsDao.count(item)

    fun loadMore() {
        addDisposable(
            pagingHelper.loadData().onIo()
        )
    }

    private var first = true

    fun loadOnce() {
        if (first) {
            first = false
            loadMore()
        }
    }

    fun searchLogo(pos: Int) {
        val tvId = pagingHelper.getList()[pos].tvId
        addDisposable(
            tvLogoSearchUseCase.execute(SearchParam(id = tvId, pos = pos)).onIo()
        )
    }

    fun fav(pos: Int) {
        addDisposable(
            updateTvUseCase.execute(
                UpdateTvParam(pos, TvFts.toTv(pagingHelper.getList()[pos]).apply {
                    favorite = !favorite
                })
            ).onIo()
        )
    }
}
