package nl.codingwithlinda.scribbledash.feature_game.result.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import nl.codingwithlinda.scribbledash.core.domain.model.DrawResult
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.components.CustomColoredButton
import nl.codingwithlinda.scribbledash.feature_game.result.presentation.state.GameResultAction

@Composable
fun GameResultScreen(
    result: DrawResult,
    onAction: (GameResultAction) -> Unit
) {
    var tMatrix = Matrix().apply {
        this.scale(.5f, .5f)
    }

    Column(
        modifier = Modifier.fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) { 
            IconButton(
                onClick = {
                    onAction(GameResultAction.Close)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close"
                )
            }
        }
        Text(text = "100%",
            style = MaterialTheme.typography.displayLarge)

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Example")
                Box(modifier = Modifier
                    .size(150.dp)
                    .drawBehind {
                        val examplePath = result.examplePath.path.asComposePath()
                        examplePath.transform(tMatrix)
                        drawPath(
                            path = examplePath,
                            color = Color.Black,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                ) {

                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Drawing")
                Box(modifier = Modifier
                    .size(50.dp)
                    .drawBehind {

                        val userPath = result.userPath.path.asComposePath()
                        userPath.transform(tMatrix)
                        drawPath(
                            path = userPath,
                            color = Color.Black,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                ) {

                }
            }
        }


        CustomColoredButton(
            text = "Try again",
            onClick = {
                onAction(GameResultAction.TryAgain)
            },
            color = Color.Blue,
            borderColor = Color.White,
            modifier = Modifier.fillMaxWidth()
        )

    }
}