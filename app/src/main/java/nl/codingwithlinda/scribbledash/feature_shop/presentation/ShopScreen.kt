package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.presentation.design_system.components.CounterComponent
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.White
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.backgroundDark
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.backgroundGradient
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.backgroundLight
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onBackground
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onSurface
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onSurfaceVariant
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.surfaceLow
import nl.codingwithlinda.scribbledash.core.presentation.util.applyIf
import nl.codingwithlinda.scribbledash.feature_shop.presentation.components.toPenShopContent
import nl.codingwithlinda.scribbledash.feature_shop.presentation.components.toShopContent
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopAction
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopUiState

@Composable
fun ShopScreen(
    accountBalance: Int,
    uiState: ShopUiState,
    onAction: (ShopAction) -> Unit
) {

    val selectedPenId by rememberUpdatedState(uiState.selectedPenId)
    var selectedTab by remember { mutableIntStateOf(0) }
    fun tabContainerColor(isSelected: Boolean): Color {
       return if (isSelected) {
           surfaceLow
       } else {
          surfaceLow.copy(.5f)
       }
    }

    fun tabContentColor(isSelected: Boolean): Color {
        return if (isSelected) {
            onBackground
        } else {
            onSurface
        }
    }

    Column(
        modifier = Modifier
            .background(
                brush = backgroundGradient
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Shop")
            CounterComponent(
                text = "$accountBalance",
                imageResourceId = R.drawable.coin,
                imageSize = 24.dp,
            )
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = onSurfaceVariant,
            indicator = { tabPositions ->

                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        tabPositions[ (selectedTab +1) % tabPositions.size]
                    ),
                    height = 4.dp,
                    color = backgroundLight
                )
            },
            divider = {
                //not desired
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(text = "Pen",
                    color = tabContentColor(selectedTab == 0)
                )
                       },
                modifier = Modifier
                    .padding(end = 4.dp)
                    .background(
                    color = tabContainerColor(selectedTab == 0), shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                    )
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(text = "Canvas",
                    color = tabContentColor(selectedTab == 1)
                ) },
                modifier = Modifier
                    .padding(start = 4.dp)
                    .background(
                    color = tabContainerColor(selectedTab == 1), shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
            )
        }


        Surface(
            color = surfaceLow
        ) {
            when (selectedTab) {
                0 -> {
                    uiState.penProducts.toPenShopContent(
                        calculatePrice = {
                            uiState.pricePenCalculator.calculatePrice(it)
                        },
                        isLocked = {
                            uiState.isPenLocked(it.id)
                        },
                        isSelected = {
                            selectedPenId == it.id
                        },
                        onItemClick = { product, price ->
                            onAction(ShopAction.ItemClick(product, price))
                        }
                    )
                }

                1 -> {
                    uiState.canvasProducts.toShopContent(
                        calculatePrice = {
                            uiState.priceCanvasCalculator.calculatePrice(it)
                        },
                        isLocked = {
                            uiState.isCanvasLocked(it.id)
                        },
                        isSelected = {
                            uiState.selectedCanvasId == it.id
                        },
                        onItemClick = { product, price ->
                            onAction(ShopAction.ItemClick(product, price))
                        }
                    )
                }
            }
        }

    }
}