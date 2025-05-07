package nl.codingwithlinda.scribbledash.feature_statistics.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.test.fakeListStatisticInfo
import nl.codingwithlinda.scribbledash.feature_statistics.presentation.components.StatisticsItem
import nl.codingwithlinda.scribbledash.feature_statistics.presentation.model.StatisticInfoUi

@Composable
fun StatisticsScreen(
    infos: List<StatisticInfoUi>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(infos) { info ->
                StatisticsItem(
                    statisticInfo = info,
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }

    }
}