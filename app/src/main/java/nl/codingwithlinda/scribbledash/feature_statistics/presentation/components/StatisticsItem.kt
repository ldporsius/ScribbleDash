package nl.codingwithlinda.scribbledash.feature_statistics.presentation.components

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.feature_statistics.presentation.model.StatisticInfoUi
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.surfaceHigh

@Composable
fun StatisticsItem(
    statisticInfo: StatisticInfoUi,
    modifier: Modifier = Modifier
) {

    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
        OutlinedCard(
            modifier = modifier
                .shadow(
                    elevation = 4.dp,
                    shape = CardDefaults.elevatedShape
                ),
            colors = CardDefaults.outlinedCardColors().copy(
                containerColor = surfaceHigh,
                contentColor = LocalContentColor.current
            )

        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(
                            statisticInfo.iconBackgroundColor,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = statisticInfo.icon),
                        contentDescription = null
                    )
                }
                Text(
                    statisticInfo.info,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                )

                Text(
                    statisticInfo.value,
                    style = MaterialTheme.typography.headlineLarge,

                )

            }
        }
    }
}

@Preview
@Composable
private fun PreviewStatisticsItem() {
    ScribbleDashTheme {
        StatisticsItem(
            statisticInfo = StatisticInfoUi(
                icon = R.drawable.ic_menu_help,
                iconBackgroundColor = Color.Blue,
                info = "Nothing to track... for now",
                value = "0%",
            ),
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
        )
    }
}