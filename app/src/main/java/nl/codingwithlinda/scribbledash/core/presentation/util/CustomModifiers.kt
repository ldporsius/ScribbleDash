package nl.codingwithlinda.scribbledash.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.applyIf(
    condition: Boolean,
    apply: Modifier.() -> Modifier
): Modifier{
    return if (condition) this.then(apply())

    else this
}