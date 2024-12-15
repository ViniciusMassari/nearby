package com.viniciusmassari.nearby.ui.screens.home

sealed class HomeUiEvent {
    data object OnFetchCategories: HomeUiEvent()
    data class OnFetchMarkets(val categoryId: String) : HomeUiEvent()
}