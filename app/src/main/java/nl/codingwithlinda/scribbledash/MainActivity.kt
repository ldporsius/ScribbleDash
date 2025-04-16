package nl.codingwithlinda.scribbledash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nl.codingwithlinda.scribbledash.core.data.AndroidDrawExampleProvider
import nl.codingwithlinda.scribbledash.core.di.AndroidAppModule
import nl.codingwithlinda.scribbledash.core.navigation.ScribbleDashApp
import nl.codingwithlinda.scribbledash.ui.theme.ScribbleDashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appModule = AndroidAppModule(this.application)
        setContent {
            ScribbleDashTheme {
               ScribbleDashApp(appModule)
            }
        }
    }
}
