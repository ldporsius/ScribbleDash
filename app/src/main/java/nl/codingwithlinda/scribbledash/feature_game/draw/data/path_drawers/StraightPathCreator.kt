package nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers

import android.graphics.Path
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asComposePath
import nl.codingwithlinda.scribbledash.core.domain.model.SingleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.PathCreator

class StraightPathCreator: PathCreator<SingleDrawPath> {
    override fun drawPath(path: List<Offset>): SimpleDrawPath {

            val mPath = Path()

            if (path.isEmpty()) return SimpleDrawPath(mPath)

            mPath.moveTo(path.first().x, path.first().y)
            for (i in 1..path.lastIndex) {
                mPath.lineTo(path[i].x, path[i].y)
            }

        return SimpleDrawPath(
            path = mPath
        )
    }
}