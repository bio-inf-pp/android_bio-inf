package com.example.bioinf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.bioinf.data.ApiService
import com.example.bioinf.data.ServerConfig
import com.example.bioinf.ui.screen.BioInfApplication
import com.example.bioinf.ui.screen.components.ServerConfigDialog
import com.example.bioinf.ui.theme.BioinfTheme
import com.example.bioinf.ui.viewmodel.IDPredictionViewModel
import com.example.bioinf.ui.viewmodel.FilePredictionViewModel
import com.example.bioinf.ui.viewmodel.TCGAPredictionViewModel

class MainActivity : ComponentActivity() {

      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            var serverUrl by remember { mutableStateOf(ServerConfig.baseUrl) }
            var showDialog by remember { mutableStateOf(true) }
            var filePredictionViewModel by remember { mutableStateOf(FilePredictionViewModel(ApiService.create(serverUrl))) }
            var idPredictionViewModel by remember { mutableStateOf(IDPredictionViewModel(ApiService.create(serverUrl))) }
            var TCGAPredictionViewModel by remember { mutableStateOf(TCGAPredictionViewModel(ApiService.create(serverUrl))) }

            BioinfTheme {
                if (showDialog) {
                    ServerConfigDialog(
                        currentUrl = serverUrl,
                        onUrlChanged = { newUrl ->
                            serverUrl = newUrl
                            filePredictionViewModel = FilePredictionViewModel(ApiService.create(newUrl))
                            idPredictionViewModel = IDPredictionViewModel(ApiService.create(newUrl))
                            TCGAPredictionViewModel = TCGAPredictionViewModel(ApiService.create(newUrl))
                        },
                        onDismiss = { showDialog = false }
                    )
                }

                BioInfApplication(
                    filePredictionViewModel = filePredictionViewModel,
                    idPredictionViewModel = idPredictionViewModel,
                    TCGAPredictionViewModel = TCGAPredictionViewModel
                )
            }
        }
    }
}