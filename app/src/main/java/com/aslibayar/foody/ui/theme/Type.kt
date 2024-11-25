package com.aslibayar.foody.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import com.aslibayar.foody.R

class Type {
    companion object {
        val dosisRegular = Font(R.font.dosis_regular).toFontFamily()
        val dosisMedium = Font(R.font.dosis_medium).toFontFamily()
        val dosisBold = Font(R.font.dosis_semibold).toFontFamily()
    }
}

class CustomTextStyle {
    companion object {
        val regularBlackSmall = TextStyle(
            fontSize = 11.sp,
            fontFamily = Type.dosisRegular,
//            color = primaryBlack,
        )
        val regularBlackMedium = TextStyle(
            fontSize = 13.sp,
            fontFamily = Type.dosisMedium,
//            color = primaryBlack,
        )
        val regularBlackLarge = TextStyle(
            fontSize = 14.sp,
            fontFamily = Type.dosisBold,
//            color = primaryBlack,
        )

        val regularBlackXLarge = TextStyle(
            fontSize = 16.sp,
            fontFamily = Type.dosisBold,
//            color = primaryBlack,
        )
        val regularPurpleSmall = TextStyle(
            fontSize = 11.sp,
            fontFamily = Type.dosisRegular,
//            color = primaryPurpleColor,
        )
        val regularPurpleMedium = TextStyle(
            fontSize = 12.sp,
            fontFamily = Type.dosisRegular,
//            color = primaryPurpleColor,
        )
        val regularPurpleLarge = TextStyle(
            fontSize = 14.sp,
            fontFamily = Type.dosisRegular,
//            color = primaryPurpleColor,
        )
        val regularLightGreySmall = TextStyle(
            fontSize = 11.sp,
            fontFamily = Type.dosisRegular,
//            color = primaryLightGray,
        )
        val regularLightGreyMedium = TextStyle(
            fontSize = 12.sp,
            fontFamily = Type.dosisRegular,
//            color = primaryLightGray,
        )
        val regularLightGreyLarge = TextStyle(
            fontSize = 14.sp,
            fontFamily = Type.dosisRegular,
//            color = primaryLightGray,
        )
    }
}


// Material 3 typography
val typography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)