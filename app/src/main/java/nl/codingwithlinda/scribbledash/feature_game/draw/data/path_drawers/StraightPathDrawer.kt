package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.core.domain.model.DrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer

class StraightPathDrawer: PathDrawer {
    override fun drawPath(path: List<Offset>, color: Int): DrawPath{
        val mPath = Path()
        mPath.moveTo(path.first().x, path.first().y)
        for(i in 1 .. path.lastIndex) {
            mPath.lineTo(path[i].x, path[i].y)
        }

        return SimpleDrawPath(
            path = mPath,
        )
    }
}