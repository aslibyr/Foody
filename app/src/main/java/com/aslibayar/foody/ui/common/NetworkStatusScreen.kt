package com.aslibayar.foody.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.aslibayar.foody.ui.theme.CustomTextStyle

@Composable
fun NetworkStatusScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "No Internet Connection",
            tint = Color.Gray,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Internet Connection",
            fontStyle = FontStyle.Italic,
            style = CustomTextStyle.regularBlackMedium,
            color = Color.Gray
        )
    }
}
