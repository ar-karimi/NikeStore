package com.ark.nikestore.data

import androidx.annotation.StringRes

data class EmptyState(
    val mustShow: Boolean,
    @StringRes val messageResId: Int = 0,
    val mustShowCallToActionBtn: Boolean = false
)
