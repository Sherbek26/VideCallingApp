package com.sherbek_mamasodiqov.videcallingapp.connect

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.sherbek_mamasodiqov.videcallingapp.VideCallingApp

class ConnectViewModel(
    private val app : Application
) : AndroidViewModel(app) {
    var state by mutableStateOf(ConnectState())
        private set

    fun onAction(action: ConnectAction){
        when(action){
            ConnectAction.onConnectClick -> {
                connectToRoom()
            }
            is ConnectAction.onNameChange -> {
                state = state.copy(name = action.name)
            }
        }
    }
    private fun connectToRoom(){
        state = state.copy(errorMessage = null)
        val userName = state.name
        if (userName.isBlank()){
            state = state.copy(
                errorMessage = "The username can't be blank"
            )
            return
        }

//        Init VideClient
        (app as VideCallingApp).initVideoClient(state.name)

        state = state.copy(isConnected = true)
    }
}
