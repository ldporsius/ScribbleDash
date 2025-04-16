package nl.codingwithlinda.scribbledash.core.data

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.PathParser
import androidx.core.graphics.drawable.toBitmap
import nl.codingwithlinda.scribbledash.R
import nl.codingwithlinda.scribbledash.core.domain.draw_examples.DrawExampleProvider
import nl.codingwithlinda.scribbledash.feature_game.draw.data.path_drawers.paths.SimpleDrawPath
import nl.codingwithlinda.scribbledash.feature_game.draw.domain.AndroidDrawPath
import org.xmlpull.v1.XmlPullParser



class AndroidDrawExampleProvider private constructor(
    private val context: Application,
): DrawExampleProvider{
    // Data class to store path information
    data class PathData(
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

    // Helper function to parse dimension values like "100dp"
    private fun parseDimensionValue(value: String): Int {

        return value.takeWhile { it.isDigit() }.toInt()

    }

    private fun parseVectorDrawable(resourceId: Int): List<PathData> {
        val resources = context.resources
        val parser = resources.getXml(resourceId)

        // For storing parsed elements
        val paths = mutableListOf<PathData>()
        var width = 0
        var height = 0
        var viewportWidth = 0f
        var viewportHeight = 0f

        try {
            var eventType = parser.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "vector" -> {
                                // Parse vector attributes
                                for (i in 0 until parser.attributeCount) {
                                    val attrName = parser.getAttributeName(i)
                                    val attrValue = parser.getAttributeValue(i)

                                    when (attrName) {
                                        "width" -> width = parseDimensionValue(attrValue)
                                        "height" -> height = parseDimensionValue(attrValue)
                                        "viewportWidth" -> viewportWidth = attrValue.toFloat()
                                        "viewportHeight" -> viewportHeight = attrValue.toFloat()
                                    }
                                }
                            }
                            "path" -> {
                                // Parse path attributes
                                var pathData = ""
                                var strokeWidth = 0f
                                var fillColor = ""
                                var strokeColor = ""

                                for (i in 0 until parser.attributeCount) {
                                    val attrName = parser.getAttributeName(i)
                                    val attrValue = parser.getAttributeValue(i)

                                    when (attrName) {
                                        "pathData" -> pathData = attrValue
                                        "strokeWidth" -> strokeWidth = attrValue.toFloat()
                                        "fillColor" -> fillColor = attrValue
                                        "strokeColor" -> strokeColor = attrValue
                                    }
                                }

                                paths.add(PathData(pathData, strokeWidth, fillColor, strokeColor))
                            }
                        }
                    }
                }
                eventType = parser.next()
            }

            // Now you have all vector data parsed
            //println("Vector size: ${width}x${height}, viewport: ${viewportWidth}x${viewportHeight}")
            println("Number of paths in vector drawable $resourceId: ${paths.size}")
            paths.forEach { println(it) }

            return paths

        } catch (e: Exception) {
            println("Error parsing vector drawable $resourceId: ${e.message}")
            e.printStackTrace()
        } finally {
            parser.close()
        }

        return emptyList()
    }

    private fun resourceToDrawPath(resource: Int): AndroidDrawPath{
        try {
            val parsedPathData = parseVectorDrawable(resource)

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