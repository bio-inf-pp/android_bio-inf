package com.example.bioinf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.bioinf.ui.viewmodel.FilePredictionViewModel
import com.example.bioinf.ui.viewmodel.IDPredictionViewModel
import com.example.bioinf.ui.viewmodel.TCGAPredictionViewModel

@Composable
fun BioInfApplication(
    filePredictionViewModel: FilePredictionViewModel,
    idPredictionViewModel: IDPredictionViewModel,
    TCGAPredictionViewModel: TCGAPredictionViewModel,
    modifier: Modifier = Modifier,
) {
    var selectedTab by remember { mutableIntStateOf(1) }

    Scaffold { contextPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(contextPadding),
            color = Color(0xFFF5F5F5)
        ) {

            Column {
                // Меню вкладок
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text(text = "По файлу") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text(text = "По ID") }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text(text = "По TCGA") }
                    )
                }

                when (selectedTab) {
                    0 -> {
                        val predictionResult by filePredictionViewModel.predictionResult.collectAsState()
                        val isLoading by filePredictionViewModel.isLoading.collectAsState()
                        val errorMessage by filePredictionViewModel.errorMessage.collectAsState()
                        val context = LocalContext.current

                        FilePredictionScreen(
                            prediction = predictionResult,
                            isLoading = isLoading,
                            errorMessage = errorMessage,
                            onClickPredict = {
                                filePredictionViewModel.predictFromFile(
                                    context
                                )
                            }
                        )
                    }

                    1 -> {
                        IDPredictionScreen(idPredictionViewModel)
                    }

                    2 -> {
                        TCGAPredictionScreen(TCGAPredictionViewModel)
                    }
                }
            }
        }
    }
}