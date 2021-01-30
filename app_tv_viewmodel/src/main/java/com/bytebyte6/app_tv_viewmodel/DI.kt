package com.bytebyte6.app_tv_viewmodel

import com.bytebyte6.app_tv_viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModule: Module = module {
    viewModel {
        VideoListViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel { PlayerViewModel() }
    viewModel {
        SearchViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel { MeViewModel(get(), get(), get()) }
    viewModel {
        PlaylistViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel { LauncherViewModel(get(), get()) }
    viewModel {
        HomeViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { UserViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DownloadViewModel(get(), get()) }
}