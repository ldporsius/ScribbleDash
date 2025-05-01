package nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser

import androidx.compose.ui.graphics.Color
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.ColoredDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer

object AndroidOffsetParser : OffsetParser<AndroidDrawPath> {
   override fun parseOffset(
       pathDrawer: PathDrawer<AndroidDrawPath>,
       pathData: PathData
   ): AndroidDrawPath {

       val path = pathDrawer.drawPath(pathData.path, pathData.color)
       return ColoredDrawPath(
           color = Color(pathData.color),
           path = path.path
       )
   }

}