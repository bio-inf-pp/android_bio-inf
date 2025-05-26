package com.example.bioinf.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bioinf.ui.viewmodel.TCGAViewModel

@Composable
fun TCGAPredictionScreen(viewModel: TCGAViewModel) {
    val prediction = viewModel.predictionResult.collectAsState().value
    var tcgaId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = tcgaId,
                    onValueChange = { tcgaId = it },
                    label = { Text("TCGA ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.predictById(tcgaId) },
                    enabled = !viewModel.isLoading.collectAsState().value,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
                ) {
                    if (viewModel.isLoading.collectAsState().value) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text("Предсказать вероятность рака")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (prediction.predictionResult != null && prediction.predictionPresent != null) {
                    Column {
                        Text(
                            text = "Вероятность рака предстательной железы:",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                        Text(
                            text = prediction.predictionResult,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF1A73E8)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Предсказано с вероятностью ${prediction.predictionPresent}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }
                }

                viewModel.errorMessage.collectAsState().value?.let { message ->
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
