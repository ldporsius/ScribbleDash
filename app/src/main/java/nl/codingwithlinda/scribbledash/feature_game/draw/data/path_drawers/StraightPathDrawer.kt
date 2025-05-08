package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidPathDrawer
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathDrawer

class StraightPathDrawer: AndroidPathDrawer{
    override fun drawPath(path: List<Offset>, color: Int): SimpleDrawPath {
        val mPath = Path()

        if(path.isEmpty()) return SimpleDrawPath(path = mPath)

        mPath.moveTo(path.first().x, path.first().y)
        for(i in 1 .. path.lastIndex) {
            mPath.lineTo(path[i].x, path[i].y)
        }

        return SimpleDrawPath(
            path = mPath,
        )
    }
}