package com.viniciusmassari.nearby.ui.screens.home

import com.google.android.gms.maps.model.LatLng
import com.viniciusmassari.nearby.data.model.Category
import com.viniciusmassari.nearby.data.model.Market

data class HomeUiState(
    val categories: List<Category>? = null,
    val markets: List<Market>? = null,
    val marketLocations: List<LatLng>? = null
)