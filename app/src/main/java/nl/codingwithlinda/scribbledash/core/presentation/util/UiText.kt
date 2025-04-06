package nl.codingwithlinda.scribbledash.core.presentation.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicText(val value: String) : UiText
    data class StringResource(@StringRes val id: Int, val args: List<Any> = emptyList()) : UiText



}
@Composable
fun UiText.asString(): String{
   return when(this){
        is UiText.DynamicText -> this.value
        is UiText.StringResource -> stringResource(id = this.id, formatArgs = this.args.toTypedArray())
    }
}