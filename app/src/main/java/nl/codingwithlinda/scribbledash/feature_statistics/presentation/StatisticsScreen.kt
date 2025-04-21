package nl.codingwithlinda.scribbledash.feature_statistics.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.test.fakeListStatisticInfo
import nl.codingwithlinda.scribbledash.feature_statistics.presentation.components.StatisticsItem

@Composable
fun StatisticsScreen() {

    val staticContent = fakeListStatisticInfo()

    Column {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(staticContent.size) { index ->
                StatisticsItem(
                    statisticInfo = staticContent[index],
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

    }
}