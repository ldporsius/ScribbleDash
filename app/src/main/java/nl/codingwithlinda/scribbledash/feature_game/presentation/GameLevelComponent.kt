package nl.codingwithlinda.scribbledash.feature_game.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import nl.codingwithlinda.scribbledash.core.domain.model.GameLevel
import nl.codingwithlinda.scribbledash.core.presentation.util.asString
import nl.codingwithlinda.scribbledash.core.presentation.util.toUi

@Composable
fun GameLevelComponent(
    gameLevel: GameLevel,
    actionOnLevel: () -> Unit,

) {
    val imageOffset = when(gameLevel){
        GameLevel.BEGINNER -> Offset(20f, -50f)
        GameLevel.CHALLENGING -> Offset(0f, 0f)
        GameLevel.MASTER -> Offset(0f, 0f)
    }
        val levelUi = gameLevel.toUi()
        Column(
            modifier = Modifier
                .width(100.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = levelUi.imageResourceId),
                contentDescription = levelUi.title.asString(),
                modifier = Modifier
                    .size(80.dp)
                    .shadow(8.dp, CircleShape)
                    .background(
                    color = MaterialTheme.colorScheme.surface,
                )
                    .clip(
                        CircleShape
                    )
                    .offset {
                        imageOffset.round()
                    }
                ,
                contentScale = ContentScale.Inside
            )

            Text(
                text = levelUi.title.asString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

}