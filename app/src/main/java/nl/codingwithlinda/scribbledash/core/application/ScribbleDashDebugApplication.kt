package nl.codingwithlinda.scribbledash.core.application

import android.app.Application
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.di.AppModule
import nl.codingwithlinda.scribbledash.core.test.AndroidTestAppModule

class ScribbleDashDebugApplication: Application() {

    companion object{
        lateinit var appModule: AppModule
    }
    override fun onCreate() {
        super.onCreate()

        appModule = AndroidTestAppModule(this)
    }
}