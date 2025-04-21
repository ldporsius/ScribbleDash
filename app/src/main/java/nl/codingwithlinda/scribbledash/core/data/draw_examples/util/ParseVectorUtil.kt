package nl.codingwithlinda.scribbledash.core.data.draw_examples.util

import org.xmlpull.v1.XmlPullParser

// Data class to store path information
data class XMLPathData(
    val pathData: String,
    val strokeWidth: Float,
    val fillColor: String,
    val strokeColor: String
)
fun parseVectorDrawable(context: android.content.Context, resourceId: Int): List<XMLPathData> {
    val resources = context.resources
    val parser = resources.getXml(resourceId)

    // Helper function to parse dimension values like "100dp"
    fun parseDimensionValue(value: String): Int {
        return value.takeWhile { it.isDigit() }.toInt()
    }
    // For storing parsed elements
    val paths = mutableListOf<XMLPathData>()
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

                            paths.add(XMLPathData(pathData, strokeWidth, fillColor, strokeColor))
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        return paths

    } catch (e: Exception) {
        println("Error parsing vector drawable $resourceId: ${e.message}")
        e.printStackTrace()
    } finally {
        parser.close()
    }

    return emptyList()
}
