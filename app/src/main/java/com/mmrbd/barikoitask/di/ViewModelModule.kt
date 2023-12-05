package com.mmrbd.barikoitask.di

import com.mmrbd.barikoitask.data.BariKoiRepository
import com.mmrbd.barikoitask.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        BariKoiRepository(
            get()
        )
    }
    viewModel<MainViewModel> {
        MainViewModel(
            get()
        )
    }
}