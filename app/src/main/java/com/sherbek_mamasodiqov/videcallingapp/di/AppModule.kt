package com.sherbek_mamasodiqov.videcallingapp.di

import com.sherbek_mamasodiqov.videcallingapp.VideCallingApp
import com.sherbek_mamasodiqov.videcallingapp.connect.ConnectViewModel
import com.sherbek_mamasodiqov.videcallingapp.video.VideoCallViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    factory {
        val app = androidContext().applicationContext as VideCallingApp
        app.client
    }

    viewModelOf(::ConnectViewModel)
    viewModelOf(::VideoCallViewModel)
}