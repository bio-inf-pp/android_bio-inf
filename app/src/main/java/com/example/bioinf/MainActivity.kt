package com.example.bioinf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.bioinf.ui.screen.BioInfApplication
import com.example.bioinf.ui.screen.IDPredictionScreen
import com.example.bioinf.ui.screen.TCGAPredictionScreen
import com.example.bioinf.ui.theme.BioinfTheme
import com.example.bioinf.ui.viewmodel.IDPredictionViewModel
import com.example.bioinf.ui.viewmodel.PredictionViewModel
import com.example.bioinf.ui.viewmodel.TCGAViewModel

class MainActivity : ComponentActivity() {
    private val predictionViewModel: PredictionViewModel by viewModels()
    private val idPredictionViewModel: IDPredictionViewModel by viewModels()
    private val TCGAViewModel: TCGAViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            BioinfTheme {
                Scaffold { contextPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contextPadding),
                        color = Color(0xFFF5F5F5)
                    ) {
                        var selectedTab by remember { mutableIntStateOf(0) }

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
                                    val predictionResult by predictionViewModel.predictionResult.collectAsState()
                                    val isLoading by predictionViewModel.isLoading.collectAsState()
                                    val errorMessage by predictionViewModel.errorMessage.collectAsState()
                                    val context = LocalContext.current

                                    BioInfApplication(
                                        prediction = predictionResult,
                                        isLoading = isLoading,
                                        errorMessage = errorMessage,
                                        onClickPredict = {
                                            predictionViewModel.predictFromFile(
                                                context
                                            )
                                        }
                                    )
                                }

                                1 -> {
                                    IDPredictionScreen(idPredictionViewModel)
                                }

                                2 -> {
                                    TCGAPredictionScreen(TCGAViewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}