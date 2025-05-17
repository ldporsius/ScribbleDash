package nl.codingwithlinda.scribbledash.feature_shop.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.presentation.design_system.components.CounterComponent
import nl.codingwithlinda.scribbledash.feature_shop.presentation.components.toPenShopContent
import nl.codingwithlinda.scribbledash.feature_shop.presentation.components.toShopContent
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopAction
import nl.codingwithlinda.scribbledash.feature_shop.presentation.state.ShopUiState

@Composable
fun ShopScreen(
    uiState: ShopUiState,
    onAction: (ShopAction) -> Unit
) {

    var selectedTab by remember { mutableIntStateOf(0) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Shop")
            CounterComponent(
                text = "300",
                imageResourceId = R.drawable.coin,
                imageSize = 20.dp,
            )
        }

        TabRow(
            selectedTabIndex = selectedTab
        ) {
            Tab(
                selected = true,
                onClick = { selectedTab = 0 },
                text = { Text(text = "Pens") }
            )
            Tab(
                selected = false,
                onClick = { selectedTab = 1 },
                text = { Text(text = "Canvas") }
            )
        }


        when(selectedTab){
            0 -> {
                uiState.penProducts.toPenShopContent(
                    calculatePrice = {
                        uiState.pricePenCalculator.calculatePrice(it)
                    },
                    isLocked = {
                        uiState.isLocked(it.id)
                    },
                    onItemClick = {
                        onAction(ShopAction.ItemClickPen(it.id))
                    }
                )
            }
            1 -> {
                uiState.canvasProducts.toShopContent(
                    calculatePrice = {
                        uiState.priceCanvasCalculator.calculatePrice(it)
                    },
                    isLocked = {
                        uiState.isLocked(it.id)
                    }
                )
            }
        }

    }
}