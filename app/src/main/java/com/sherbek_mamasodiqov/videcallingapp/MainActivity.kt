package com.sherbek_mamasodiqov.videcallingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sherbek_mamasodiqov.videcallingapp.connect.ConnectScreen
import com.sherbek_mamasodiqov.videcallingapp.connect.ConnectViewModel
import com.sherbek_mamasodiqov.videcallingapp.ui.theme.VideCallingAppTheme
import com.sherbek_mamasodiqov.videcallingapp.video.CallState
import com.sherbek_mamasodiqov.videcallingapp.video.VideoCallScreen
import com.sherbek_mamasodiqov.videcallingapp.video.VideoCallViewModel
import io.getstream.video.android.compose.theme.VideoTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VideCallingAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = ConnectRoute.toString(), modifier = Modifier.padding(innerPadding)){
                        composable(route = ConnectRoute.toString()){
                            val viewModel = koinViewModel<ConnectViewModel>()
                            val state = viewModel.state
                            
                            LaunchedEffect(key1 = state.isConnected) {
                                if (state.isConnected) {
                                    navController.navigate(VideoCallRoute.toString()){
                                        popUpTo(ConnectRoute.toString()){
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            ConnectScreen(state = state, onAction = viewModel::onAction)
                        }

                        composable(route = VideoCallRoute.toString()){
                            val viewModel = koinViewModel<VideoCallViewModel>()
                            val state = viewModel.state
                            LaunchedEffect(key1 = state.callState) {
                                if (state.callState == CallState.ENDED) {
                                    navController.navigate(ConnectRoute.toString()){
                                        popUpTo(VideoCallRoute.toString()){
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            VideoTheme {
                                VideoCallScreen(state = state, onAction = viewModel::onAction)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
data object ConnectRoute

@Serializable
data object VideoCallRoute