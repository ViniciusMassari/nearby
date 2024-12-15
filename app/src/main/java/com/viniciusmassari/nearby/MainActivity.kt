package com.viniciusmassari.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.viniciusmassari.nearby.data.model.Market
import com.viniciusmassari.nearby.ui.screens.home.HomeScreen
import com.viniciusmassari.nearby.ui.screens.home.HomeUiState
import com.viniciusmassari.nearby.ui.screens.home.HomeViewModel
import com.viniciusmassari.nearby.ui.screens.market_details.MarketDetailsScreen
import com.viniciusmassari.nearby.ui.screens.market_details.MarketDetailsUiEvent
import com.viniciusmassari.nearby.ui.screens.market_details.MarketDetailsViewModel
import com.viniciusmassari.nearby.ui.screens.qrcode_scanner.QRCodeScannerScreen
import com.viniciusmassari.nearby.ui.screens.splash.SplashScreen
import com.viniciusmassari.nearby.ui.screens.welcome.WelcomeScreen
import com.viniciusmassari.nearby.ui.screens.routes.Home
import com.viniciusmassari.nearby.ui.screens.routes.QRCodeScanner
import com.viniciusmassari.nearby.ui.screens.routes.Splash
import com.viniciusmassari.nearby.ui.screens.routes.Welcome
import com.viniciusmassari.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()

                val homeViewModel by viewModels<HomeViewModel>()
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

                val marketDetailsViewModel by viewModels<MarketDetailsViewModel>()
                val marketDetailsUiState by marketDetailsViewModel.uiState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = Splash
                ) {
                    composable<Splash> {
                        SplashScreen(
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }
                    composable<Welcome> {
                        WelcomeScreen(
                            onNavigateToHome = {
                                navController.navigate(Home)
                            }
                        )
                    }
                    composable<Home> {
                        HomeScreen(
                            onNavigateToMarketDetails = { selectedMarket ->
                                navController.navigate(selectedMarket)
                            },
                            uiState = homeUiState,
                            onEvent = homeViewModel::onEvent
                        )
                    }
                    composable<Market> {
                        val selectedMarket = it.toRoute<Market>()

                        MarketDetailsScreen(
                            market = selectedMarket,
                            uiState = marketDetailsUiState,
                            onEvent = marketDetailsViewModel::onEvent,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onNavigateToQRCodeScanner = {
                                navController.navigate(QRCodeScanner)
                            }
                        )
                    }

                    composable<QRCodeScanner> {
                        QRCodeScannerScreen(
                            onCompletedScan = { qrCodeContent ->
                                if (qrCodeContent.isNotEmpty()) {
                                    marketDetailsViewModel.onEvent(
                                        MarketDetailsUiEvent.OnFetchCoupon(
                                            qrCodeContent
                                        )
                                    )
                                    navController.popBackStack()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NearbyTheme {

    }
}