package com.sherbek_mamasodiqov.videcallingapp

import android.app.Application
import com.sherbek_mamasodiqov.videcallingapp.di.appModule
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VideCallingApp : Application() {
    private var currentName : String? = null
    var client : StreamVideo? = null

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@VideCallingApp)
            modules(appModule)
        }
    }

    fun initVideoClient(userName : String) {
        if (client == null || userName != currentName){
            StreamVideo.removeClient()
            currentName = userName

            client = StreamVideoBuilder(
                context = this,
                apiKey = "n6phqh93emyq",
                user = User(
                    id = userName,
                    name = userName,
                    type = UserType.Guest
                )
            ).build()
        }
    }
}