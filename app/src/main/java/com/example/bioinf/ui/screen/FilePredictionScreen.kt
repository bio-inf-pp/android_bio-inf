package com.example.bioinf.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bioinf.ui.viewmodel.PredictionResult

@Composable
fun FilePredictionScreen(
    modifier: Modifier = Modifier,
    prediction: PredictionResult,
    isLoading: Boolean,
    errorMessage: String?,
    onClickPredict: (Context) -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onClickPredict(context) },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A73E8))
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Загрузить файл и предсказать")
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
                    text = "Предсказано с вероятностью: ${prediction.predictionPresent}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
        }


        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red
            )
        }
    }
}