package nl.codingwithlinda.scribbledash.core.data.draw_examples

import android.app.Application
import androidx.core.graphics.PathParser
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.parseVectorDrawable
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import org.xmlpull.v1.XmlPullParser


class AndroidDrawExampleProvider private constructor(
    private val context: Application,
): DrawExampleProvider{
    // Data class to store path information
    data class XMLPathData(
        val pathData: String,
        val strokeWidth: Float,
        val fillColor: String,
        val strokeColor: String
    )

    private val examplesResources = listOf(
        R.drawable.alien,
        R.drawable.bicycle,
        R.drawable.boat,
        R.drawable.book,
        R.drawable.butterfly,

        R.drawable.camera,
        R.drawable.car,
        R.drawable.castle,
        R.drawable.cat,
        R.drawable.clock,
        R.drawable.crown,
        R.drawable.cup,

        R.drawable.dog,

        R.drawable.envelope,
        R.drawable.eye,

        R.drawable.fish,
        R.drawable.flower,
        R.drawable.football_field,
        R.drawable.frog,

        R.drawable.glasses,

        R.drawable.heart,
        R.drawable.helicotper,
        R.drawable.hotairballoon,
        R.drawable.house,

        R.drawable.moon,
        R.drawable.mountains,

        R.drawable.rocket,
        R.drawable.robot,

        R.drawable.smiley,
        R.drawable.snowflake,
        R.drawable.sofa,
        R.drawable.star,

        R.drawable.train,
        R.drawable.umbrella,
        R.drawable.whale,

        )


    companion object{
        @Volatile
        private var _instance: AndroidDrawExampleProvider? = null


        fun getInstance(context: Application) =
            _instance ?: synchronized(this){
                _instance ?: AndroidDrawExampleProvider(context).also { _instance = it }
            }

    }

    override val examples: List<AndroidDrawPath> by lazy {
        examplesResources.map {
            resourceToDrawPath(it)
        }
    }

    private fun resourceToDrawPath(resource: Int): AndroidDrawPath{
        try {
            val parsedPathData = parseVectorDrawable(
                context,
                resource)

            val parsedPath = parsedPathData.map {pd ->
                PathParser.createPathFromPathData(pd.pathData)
            }.let {
                android.graphics.Path().apply {
                    it.forEach { path ->
                        addPath(path)
                    }
                }
            }
            if (parsedPath.isEmpty)
                return SimpleDrawPath(
                    path = PathParser.createPathFromPathData("M0,0 L100,100")
                )

            return SimpleDrawPath(
                path = parsedPath
            )
        }catch (e: Exception){
            e.printStackTrace()
        }

        return SimpleDrawPath(
            path = PathParser.createPathFromPathData("M0,0 L100,100")
        )

    }
}