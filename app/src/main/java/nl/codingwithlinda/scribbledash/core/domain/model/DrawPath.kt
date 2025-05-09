package nl.codingwithlinda.scribbledash.core.domain.model

import android.graphics.Path

interface DrawPath
interface SingleDrawPath: DrawPath{
    val path: Path
}
interface MultipleDrawPath: DrawPath{
    val paths: List<Path>
}

