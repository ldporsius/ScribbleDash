package nl.codingwithlinda.scribbledash.core.domain.model

import android.graphics.Path
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.ColoredPath

interface DrawPath
interface SingleDrawPath: DrawPath{
    val path: Path
}
interface MultipleDrawPath: DrawPath{
    val paths: List<Path>
}
interface MultiColorDrawPath: DrawPath{
    val paths: List<ColoredPath>
}
