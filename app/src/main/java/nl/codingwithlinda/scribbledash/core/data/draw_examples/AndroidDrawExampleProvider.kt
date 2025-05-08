package nl.codingwithlinda.scribbledash.core.data.draw_examples

import android.app.Application
import android.graphics.Path
import android.graphics.PathMeasure
import androidx.compose.ui.graphics.asComposePath
import androidx.core.graphics.PathParser
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.parseVectorDrawable
import nl.codingwithlinda.scribbledash.core.data.draw_examples.util.pathToCoordinates
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleCoordinatesDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.CoordinatesDrawPath


class AndroidDrawExampleProvider private constructor(
    private val context: Application,
): DrawExampleProvider{

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
        //R.drawable.star, todo this is empty vector

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

   /* override val examples: List<AndroidDrawPath> by lazy {
       val paths =  examplesResources.map {
            resourceToDrawPaths(it)
        }

        val flattened = paths.map { drawPaths ->
            flattenPaths(drawPaths.map { it.path })
        }


        flattened
            .filter {
                !it.isEmpty
            }
            .map {
            SimpleDrawPath(
                path = it
            )
        }
    }*/
    override val examples: List<CoordinatesDrawPath> by lazy {
        val paths =  examplesResources.map {
            resourceToDrawPaths(it)
        }

        val flattened = paths.map { drawPaths ->
            flattenPaths(drawPaths.map { it.path })
        }


        flattened
            .filter {
                !it.isEmpty
            }
            .map {
             val coors = pathToCoordinates(it)
                SimpleCoordinatesDrawPath(coors)
            }
    }

    fun getByResId(resId: Int): AndroidDrawPath{
        return SimpleDrawPath(
            path = flattenPaths(resourceToDrawPaths(resId).map { it.path })
        )
    }

    private fun resourceToDrawPaths(resource: Int): List<AndroidDrawPath>{
        try {
            val parsedPathData = parseVectorDrawable(
                context,
                resource)

            val parsedPaths = parsedPathData.map {pd ->
                PathParser.createPathFromPathData(pd.pathData)
            }.map {parsedPath ->
                SimpleDrawPath(
                path = parsedPath
            )
            }

            return parsedPaths

        }catch (e: Exception){
            e.printStackTrace()
        }

        return emptyList()
    }

    private fun pathsToCoordinates(paths: List<Path>): List<PathCoordinates>{
        return paths.flatMap { path ->
            pathToCoordinates(path)
        }
    }


    private fun flattenPaths(paths: List<Path>): Path {
        val combinedPath = Path()
        paths.forEach { path ->
            flattenPath(path).also {
                combinedPath.addPath(it)
            }
        }
        return combinedPath
    }

    private fun flattenPath(path: Path): Path{

        // Create a PathMeasure to analyze your original path
        val originalPath = path

        val pathMeasure: PathMeasure = PathMeasure(originalPath, false)

// Create a new path that will hold our approximation
        val approximatePath = Path()

// The distance increment - smaller values give better approximation
        val distanceIncrement = 1.0f // adjust based on your needs
        val pathLength: Float = pathMeasure.length
        var distance = 0f

        val coordinates = FloatArray(2)
        val tangent = FloatArray(2)


// First point
        pathMeasure.getPosTan(0f, coordinates, tangent)
        approximatePath.moveTo(coordinates[0], coordinates[1])


// Sample points along the path
        while (distance < pathLength) {
            distance += distanceIncrement
            if (distance > pathLength) distance = pathLength

            pathMeasure.getPosTan(distance, coordinates, tangent)
            approximatePath.lineTo(coordinates[0], coordinates[1])
        }
        return approximatePath
    }


}

data class PathCoordinates(
    val x: Float,
    val y: Float
)