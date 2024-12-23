package com.aslibayar.foody.components.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aslibayar.foody.ui.theme.LightOrange
import com.aslibayar.foody.ui.theme.Orange


@Composable
fun CustomOutlinedTextField(
    modifier: Modifier,
    label: String,
    text: String,
    returnText: (String) -> Unit,
    onImeClicked: () -> Unit,
    keyboardOptions: KeyboardOptions,
    maxLength: Int? = null,
    errorMessage: String? = null
) {

    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = text,
        onValueChange = { output ->
            if (maxLength != null) {
                if (output.length <= maxLength) returnText(output)
            } else {
                returnText(output)
            }
        },
        modifier = modifier.onFocusChanged {
            isFocused = it.hasFocus
        },
        label = {
            Text(
                text = label,
                fontSize = 11.sp
            )
        },
        trailingIcon = {
            AnimatedVisibility(isFocused && text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        returnText("")
                    })
            }
        },
        isError = !errorMessage.isNullOrEmpty(),
        supportingText = {
            errorMessage?.let { it1 ->
                Text(
                    text = it1,
                    fontSize = 11.sp,
                    color = Color.Red
                )
            }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                onImeClicked()
            },
            onGo = {
                onImeClicked()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Orange,
            unfocusedIndicatorColor = Orange,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Orange,
            unfocusedLabelColor = LightOrange,
            cursorColor = Orange,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        shape = RoundedCornerShape(30.dp),
        maxLines = 1,
        singleLine = true,
    )
}