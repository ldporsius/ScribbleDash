package nl.codingwithlinda.scribbledash.core.presentation.design_system.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.headlineXSmall
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.onBackground
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.surfaceLow

@Composable
fun CounterComponent(
    text: String,
    imageResourceId: Int,
    imageSize: Dp,
    backgroundColor: Color = surfaceLow,
    foregroundColor: Color = onBackground,
    modifier: Modifier = Modifier.size(width = 76.dp, height = 36.dp)
) {


        ConstraintLayout(
            modifier = modifier
        ) {
            val (counter, image) = createRefs()

            val guideLine = createGuidelineFromStart(.5f)
                //.reference.withHorizontalChainParams(endMargin = imageSize)

            val guideLineText = createGuidelineFromTop(imageSize/2)
            Box(modifier = Modifier
                .wrapContentWidth()
                .constrainAs(counter) {
                    start.linkTo(guideLine, margin = (-imageSize))
                    centerVerticallyTo(image)
                    //top.linkTo(parent.top)
                    //bottom.linkTo(parent.bottom)

                }
                .background(color = backgroundColor, shape = RoundedCornerShape(50))
                .padding(end = 8.dp)
                ,
                contentAlignment = Alignment.CenterStart

            ) {
                Text(text,
                    style = headlineXSmall,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = imageSize)
                        ,
                    color = foregroundColor,
                    textAlign = TextAlign.Start
                )
            }

            Image(
                painter = painterResource(imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .constrainAs(image){
                        //start.linkTo(parent.start)
                        end.linkTo(guideLine, margin = imageSize/2)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ,
                contentScale = ContentScale.Fit
            )
        }

}