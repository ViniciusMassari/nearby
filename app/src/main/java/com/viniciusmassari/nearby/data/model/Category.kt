package com.viniciusmassari.nearby.data.model

import androidx.annotation.DrawableRes
import com.viniciusmassari.nearby.ui.components.category.CategoryFilterChipView

data class Category(
    val id: String,
    val name: String
){
    @get: DrawableRes
    val icon: Int? get() = CategoryFilterChipView.Companion.fromDescription(description = name)?.icon
}
