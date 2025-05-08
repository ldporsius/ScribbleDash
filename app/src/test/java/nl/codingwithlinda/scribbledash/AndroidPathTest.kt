package nl.codingwithlinda.scribbledash

import android.graphics.Path
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidPathTest {

    @Test
    fun testAndroidPath() {
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(100f, 100f)
        path.close()

        assertFalse(path.isEmpty)
    }
}