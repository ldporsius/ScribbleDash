package nl.codingwithlinda.scribbledash.core.di

import android.app.Application
import nl.codingwithlinda.scribbledash.core.data.draw_examples.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.presentation.util.RatingTextGenerator

class AndroidAppModule(
    private val application: Application
): AppModule {

    override val drawExampleProvider: AndroidDrawExampleProvider
        get() = AndroidDrawExampleProvider.getInstance(application)

    val ratingTextGenerator: RatingTextGenerator = RatingTextGenerator(application)
}