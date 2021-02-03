package com.bytebyte6.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.koin.dsl.module

/**
 * 测试专用
 */
val roomMemoryModule = module {
    single { Room.inMemoryDatabaseBuilder(get(), AppDatabase::class.java).build() }
    single { ApplicationProvider.getApplicationContext<Context>() }
}