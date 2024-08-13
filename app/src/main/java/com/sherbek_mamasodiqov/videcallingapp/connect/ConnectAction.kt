package com.sherbek_mamasodiqov.videcallingapp.connect

sealed interface ConnectAction {
    data class onNameChange(val name : String) : ConnectAction
    data object onConnectClick : ConnectAction
}