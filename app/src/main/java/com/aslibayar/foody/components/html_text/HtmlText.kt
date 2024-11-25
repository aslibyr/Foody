package com.aslibayar.foody.components.html_text

import android.text.Html
import android.text.Spanned
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString


@Composable
fun HtmlText(
    html: String,
    textStyle: androidx.compose.ui.text.TextStyle
) {
    val parsedText = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    val annotatedString = parsedText.toAnnotatedString()

    BasicText(
        text = annotatedString,
        style = textStyle
    )
}

// Extension function to convert Spanned (HTML parsed text) to AnnotatedString
fun Spanned.toAnnotatedString(): AnnotatedString {
    return buildAnnotatedString {
        append(this@toAnnotatedString.toString())
    }
}