package nl.codingwithlinda.scribbledash.feature_game.draw.domain

import android.graphics.Path
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath

interface AndroidDrawPath: DrawPath{
    override val path: Path
}