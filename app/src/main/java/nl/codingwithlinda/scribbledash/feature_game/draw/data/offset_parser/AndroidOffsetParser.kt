package nl.codingwithlinda.scribbledash.feature_game.draw.data.offset_parser

import nl.codingwithlinda.scribbledash.core.domain.model.PathCoordinates
import nl.codingwithlinda.scribbledash.core.domain.offset_parser.OffsetParser
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathData

object AndroidOffsetParser : OffsetParser{
   override fun parseOffset(
       pathData: PathData
   ): List<PathCoordinates> {

       val paths = pathData.path.map {
           PathCoordinates(x = it.x, y= it.y)
       }
       return paths
   }

}