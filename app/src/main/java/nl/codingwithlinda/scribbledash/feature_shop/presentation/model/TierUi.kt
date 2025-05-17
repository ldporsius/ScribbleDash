package nl.codingwithlinda.scribbledash.feature_shop.presentation.model

import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText

data class TierUi(
    val tier: Tier,
    val containerColor: androidx.compose.ui.graphics.Color,
    val color: androidx.compose.ui.graphics.Color,
    val title: UiText
)
