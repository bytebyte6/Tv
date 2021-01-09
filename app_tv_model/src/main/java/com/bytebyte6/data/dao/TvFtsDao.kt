package com.bytebyte6.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.bytebyte6.data.PAGE_SIZE
import com.bytebyte6.data.entity.TvFts
import io.reactivex.rxjava3.core.Single

@Dao
interface TvFtsDao {
    @Query("SELECT * FROM TvFts WHERE TvFts MATCH :key ")
    fun search(key: String): Single<List<TvFts>>

    @Query("SELECT * FROM TvFts WHERE TvFts MATCH :key ")
    fun searchLiveData(key: String): LiveData<List<TvFts>>

    @Query("SELECT COUNT(*) FROM TvFts WHERE TvFts MATCH :key ")
    fun count(key: String): Int

    @Query("SELECT COUNT(*) FROM TvFts WHERE TvFts MATCH :key ")
    fun countLiveData(key: String): LiveData<Int>

    @Query("SELECT * FROM TvFts WHERE TvFts MATCH :key LIMIT $PAGE_SIZE OFFSET :offset")
    fun paging(offset: Int, key: String): List<TvFts>
}