package com.sherbek_mamasodiqov.videcallingapp.video

sealed interface VideoCallAction {
    data object onDisconnectClick : VideoCallAction
    data object JoinCall : VideoCallAction

}
