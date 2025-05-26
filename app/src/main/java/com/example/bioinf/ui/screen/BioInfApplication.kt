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

@Composable
fun BioInfApplication(
    modifier: Modifier = Modifier,
    predictionResult: Float?,
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

        Text(
            text = "Результат: ${predictionResult}",
            style = MaterialTheme.typography.headlineSmall
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red
            )
        }
    }
}