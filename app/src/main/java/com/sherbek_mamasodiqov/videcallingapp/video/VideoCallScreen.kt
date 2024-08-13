package com.sherbek_mamasodiqov.videcallingapp.video

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.controls.actions.DefaultOnCallActionHandler
import io.getstream.video.android.core.call.state.LeaveCall

@Composable
fun VideoCallScreen(
    state: VideoCallState,
    onAction : (VideoCallAction) -> Unit
){
    when{
        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = state.error, color = MaterialTheme.colorScheme.error, fontSize = 18.sp)
            }
        }
        state.callState == CallState.JOINING -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Joining...")
            }
        }
        else -> {
            val context = LocalContext.current
            val basePermissions = listOf(
                "android.permission.CAMERA",
                "android.permission.RECORD_AUDIO",
            )
            val bluetoothPermissions =
                if(Build.VERSION.SDK_INT >= 31) {
                    listOf(
                        "android.permission.BLUETOOTH_CONNECT"
                    )
                }else{
                    emptyList()
                }
            val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
                listOf("android.permission.POST_NOTIFICATIONS")
            }else{
                emptyList()
            }
            CallContent(call = state.call,
                modifier = Modifier.fillMaxSize(),
                permissions = rememberCallPermissionsState(
                    call = state.call,
                    permissions = basePermissions+bluetoothPermissions+notificationPermission,
                    onPermissionsResult = {permissions->
                        if (permissions.values.contains(false)){
                            Toast.makeText(context,"Not all permissions granted.Please gran all permissions!",Toast.LENGTH_LONG).show()
                        }else{
                            onAction(VideoCallAction.JoinCall)
                        }
                    }
                ),
                onCallAction = {action->
                    if (action == LeaveCall) {
                        onAction(VideoCallAction.onDisconnectClick)
                    }
                    DefaultOnCallActionHandler.onCallAction(state.call,action)
                },
                onBackPressed = {
                    onAction(VideoCallAction.onDisconnectClick)
                }
            )
        }
    }
}
