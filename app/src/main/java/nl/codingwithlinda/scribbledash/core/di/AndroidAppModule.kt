package nl.codingwithlinda.scribbledash.core.di

import android.app.Application
import nl.codingwithlinda.scribbledash.core.data.AndroidDrawExampleProvider

class AndroidAppModule(
    private val application: Application
): AppModule {

    override val drawExampleProvider: AndroidDrawExampleProvider
        get() = AndroidDrawExampleProvider.getInstance(application)
}