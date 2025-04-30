package com.example.bioinf.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BioInfApplication(
    modifier: Modifier = Modifier,
    predictionResult: Float?,
    isLoading: Boolean,
    errorMessage: String?,
    onPredictRequest: (List<Double>) -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поле для ввода фичей (упрощенный пример)
        val feature1 = remember { mutableStateOf("1.0") }
        val feature2 = remember { mutableStateOf("2.5") }
        val feature3 = remember { mutableStateOf("3.0") }

        OutlinedTextField(
            value = feature1.value,
            onValueChange = { feature1.value = it },
            label = { Text("Feature 1") }
        )

        OutlinedTextField(
            value = feature2.value,
            onValueChange = { feature2.value = it },
            label = { Text("Feature 2") }
        )

        OutlinedTextField(
            value = feature3.value,
            onValueChange = { feature3.value = it },
            label = { Text("Feature 3") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val features = listOf(
                    feature1.value.toDoubleOrNull() ?: 0.0,
                    feature2.value.toDoubleOrNull() ?: 0.0,
                    feature3.value.toDoubleOrNull() ?: 0.0
                )
                onPredictRequest(features)
            },
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text("Сделать предсказание")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        predictionResult?.let {
            Text(
                text = "Результат: ${"%.2f".format(it)}",
                style = MaterialTheme.typography.headlineSmall,
                color = if (it >= 0) Color.Unspecified else Color.Red
            )
        }
    }
}