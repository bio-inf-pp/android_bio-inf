package com.example.bioinf

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.bioinf.ui.screen.BioInfApplication
import com.example.bioinf.ui.theme.BioinfTheme
import com.example.bioinf.ui.viewmodel.PredictionViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: PredictionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            BioinfTheme {
                val predictionResult by viewModel.predictionResult.collectAsState()
                val isLoading by viewModel.isLoading.collectAsState()
                val errorMessage by viewModel.errorMessage.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BioInfApplication(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        predictionResult = predictionResult,
                        isLoading = isLoading,
                        errorMessage = errorMessage,
                        onPredictRequest = { features ->
                            viewModel.makePrediction(features)
                        }
                    )
                }

                errorMessage?.let { message ->
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    viewModel.clearError()
                }
            }
        }
    }
}
