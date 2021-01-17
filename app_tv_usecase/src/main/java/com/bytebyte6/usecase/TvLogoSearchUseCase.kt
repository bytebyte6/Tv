package com.bytebyte6.usecase

import com.bytebyte6.base.RxSingleUseCase
import com.bytebyte6.data.dao.TvDao
import com.bytebyte6.data.work.SearchImageImpl

class TvLogoSearchUseCase(
    private val imageSearch: SearchImageImpl,
    private val tvDao: TvDao
) : RxSingleUseCase<SearchParam, SearchParam>() {
    override fun doSomething(param: SearchParam): SearchParam {
        val tv = tvDao.getTv(param.id)
        if (tv.logo.isEmpty()) {
            val logo = imageSearch.search(tv.name.replace("&", " "))
            if (logo.isNotEmpty()) {
                tv.logo = logo
                tvDao.update(tv)
                param.logo = logo
                return param
            }
        }
        return param
    }
}

data class SearchParam(
    var id: Long,
    var pos: Int,
    var logo: String = ""
)