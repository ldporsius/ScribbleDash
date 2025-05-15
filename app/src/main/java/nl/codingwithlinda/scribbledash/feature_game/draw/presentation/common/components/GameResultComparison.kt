package nl.codingwithlinda.scribbledash.feature_game.draw.presentation.common.components

import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import nl.codingwithlinda.scribbledash.core.data.AndroidBitmapPrinter
import nl.codingwithlinda.scribbledash.core.data.util.centerPath
import nl.codingwithlinda.scribbledash.core.data.util.toBitmapUiOnly
import nl.codingwithlinda.scribbledash.core.domain.result_manager.ResultCalculator
import nl.codingwithlinda.scribbledash.feature_game.draw.presentation.endless_mode.result.EndlessResultUiState
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme

@Composable
fun GameResultComparison(
    examplePath: Path,
    userPath: List<Path>,
    checkIcon: @Composable () -> Unit,
    ) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ,
        horizontalArrangement = Arrangement.Center


    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {

            val (example, user, check) = createRefs()
            val centerGuideline = createGuidelineFromStart(.5f)
            val bottomGuideline = createGuidelineFromTop(100.dp)
            Column(
                modifier = Modifier
                    .width(125.dp)
                    .graphicsLayer {
                        this.rotationZ = -10f
                    }
                    .constrainAs(example) {
                        top.linkTo(parent.top)
                        end.linkTo(centerGuideline)
                    }

                        ,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Example",
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedCard(
                    modifier = Modifier.shadow(
                        8.dp
                    )
                ) {

                    Canvas(
                        modifier = Modifier
                            .width(120.dp)
                            .aspectRatio(1f)
                    ) {
                        centerPath(examplePath)
                        drawPath(
                            path = examplePath.asComposePath(),
                            color = Color.Black,
                            style = Stroke(width = 2.dp.toPx())
                        )
                    }
                }
            }

            Column(
                modifier = Modifier

                    .width(126.dp)
                    .graphicsLayer {
                        this.rotationZ = 10f
                    }
                    .constrainAs(user) {
                        top.linkTo(parent.top)
                        start.linkTo(centerGuideline)
                        //end.linkTo(parent.end)
                    }
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Drawing",
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedCard(
                    modifier = Modifier.shadow(
                        8.dp
                    )
                ) {

                    Canvas(
                        modifier = Modifier
                            .width(120.dp)
                            .aspectRatio(1f)
                    ) {

                        val w = size.width
                        val h = size.height
                        val requiredSize = 100.dp.toPx().toInt()
                        val strokeWidth = 5.dp.toPx()
                        val bm = userPath.toBitmapUiOnly(
                            requiredSize = requiredSize,
                            basisStrokeWidth = strokeWidth
                        )
                        val dx = w / 2 - bm.width / 2
                        val dy = h / 2 - bm.height / 2
                        drawImage(
                            bm.asImageBitmap(),
                            topLeft = Offset(dx, dy),
                            style = Stroke(4.dp.toPx())
                        )
                    }
                }
            }

            Box(modifier = Modifier
                .constrainAs(check){
                    top.linkTo(bottomGuideline)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .size(84.dp)
            ) {
                checkIcon()
            }
        }
    }
}

@Preview
@Composable
private fun GameResultComparisonPreview() {

    val uiState = EndlessResultUiState(
        examplePath = Path(),
        userPath = emptyList()
    )

    ScribbleDashTheme {
        GameResultComparison(
            examplePath = Path(),
            userPath = emptyList(),
            checkIcon = {
                uiState.CheckIcon()
            }
        )
    }
}