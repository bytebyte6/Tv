package com.bytebyte6.view.usecase

import com.bytebyte6.data.RxSingleUseCase
import com.bytebyte6.data.dao.TvFtsDao
import com.bytebyte6.data.entity.Tv
import com.bytebyte6.data.entity.TvFts

class SearchTvUseCase(private val tvFtsDao: TvFtsDao) : RxSingleUseCase<String, List<Tv>>() {
    override fun doSomething(param: String): List<Tv> {
        val list = tvFtsDao.search(param)
        return list.map {
            TvFts.toIpTv(it)
        }
    }
}