package nl.codingwithlinda.scribbledash.core.data

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DrawExampleProviderTest{

    private lateinit var context: Application
    private lateinit var provider : AndroidDrawExampleProvider

    @Before
    fun setup(){
        context = ApplicationProvider.getApplicationContext<Application>()
        provider = AndroidDrawExampleProvider.getInstance(context)
    }
    @Test
    fun testProviderExamplesAreLoadedOnce(): Unit = runBlocking(){

        val provider0 = AndroidDrawExampleProvider.getInstance(context)
        val examples0 = provider0.examples

        examples0.forEach {
            assertFalse(it.path.isEmpty())
        }

        val provider1 = AndroidDrawExampleProvider.getInstance(context)
        val examples1 = provider1.examples
        examples1.forEach {
            assertFalse(it.path.isEmpty())
        }

        assertSame(provider0, provider1)

        assertTrue(examples0 == examples1)
        assertSame(examples0, examples1)
    }
}