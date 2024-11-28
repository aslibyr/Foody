package com.aslibayar.foody.components.autocomplete

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AutoCompleteComponent(
    modifier: Modifier = Modifier,
    list: List<String>,
    onTextClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Arama Önerileri",
        )
        Spacer(modifier = Modifier.height(10.dp))
        list.forEach { autoCompleteText ->
            HorizontalDivider()
            Text(
                text = autoCompleteText,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onTextClick.invoke(autoCompleteText)
                    },
            )
        }
    }
}