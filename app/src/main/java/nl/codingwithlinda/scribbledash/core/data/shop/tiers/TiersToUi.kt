package nl.codingwithlinda.scribbledash.core.data.shop.tiers

import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.domain.model.shop.tiers.Tier
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.BasicTierColor
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.LegendaryTierColor
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.PremiumTierColor
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onBackground
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onPrimary
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onSurfaceVariant
import nl.codingwithlinda.scribbledash.core.presentation.util.UiText
import nl.codingwithlinda.scribbledash.feature_shop.presentation.model.TierUi

val tiersUi = listOf(
    TierUi(
        tier = Tier.FREE,
        containerColor = BasicTierColor,
        color = onBackground,
        title = UiText.DynamicText("Free")
    ),
    TierUi(
        tier = Tier.BASIC,
        containerColor = BasicTierColor,
        color = onBackground,
        title = UiText.DynamicText("Basic")
    ),
    TierUi(
        tier = Tier.PREMIUM,
        containerColor = PremiumTierColor,
        color = onPrimary,
        title = UiText.DynamicText("Premium")
    ),
    TierUi(
        tier = Tier.LEGENDARY,
        containerColor = LegendaryTierColor,
        color = onPrimary,
        title = UiText.DynamicText("Legendary")
    ),
)

fun tierToContainerColor(tier: Tier): Color {
    return tiersUi.firstOrNull { it.tier == tier }?.containerColor ?: BasicTierColor
}
fun tierToColor(tier: Tier): Color {
   return tiersUi.firstOrNull { it.tier == tier }?.color ?: onPrimary
}