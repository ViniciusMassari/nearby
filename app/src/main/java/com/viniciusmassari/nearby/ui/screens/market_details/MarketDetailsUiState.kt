package com.viniciusmassari.nearby.ui.screens.market_details

import com.viniciusmassari.nearby.data.model.Rule

data class MarketDetailsUiState(
    val rules: List<Rule>? = null,
    val coupon: String? = null,
)