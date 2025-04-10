package nl.codingwithlinda.scribbledash.feature_game.test_data

import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer

class FakePathDrawer: PathDrawer<DrawPath> {
    override fun drawPath(path: List<Offset>, color: Int): DrawPath {
        println("FAKE DRAW PATH")
        return object : DrawPath{

        }

    }
}