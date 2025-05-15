package nl.codingwithlinda.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import nl.codingwithlinda.scribbledash.core.application.ScribbleDashApplication
import nl.codingwithlinda.scribbledash.core.application.ScribbleDashDebugApplication
import nl.codingwithlinda.scribbledash.core.navigation.ScribbleDashApp
import nl.codingwithlinda.scribbledash.core.presentation.design_system.theme.ScribbleDashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appModule =
            if (BuildConfig.DEBUG){
                println("MAIN ACTIVITY DEBUG MODE")
                ScribbleDashDebugApplication.appModule

            }else{
                ScribbleDashApplication.appModule
            }

        setContent {
            LaunchedEffect(true) {
                appModule.gamesManager.clear()
            }
            ScribbleDashTheme {
               ScribbleDashApp(appModule)
            }
        }
    }
}
