package com.ssafy.workalone.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.R

private val notosanskr = FontFamily(
    Font(R.font.notosanskr_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.notosanskr_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.notosanskr_regular, FontWeight.Normal, FontStyle.Normal)
)
private val spoqahansans = FontFamily(
    Font(R.font.spoqahansansneo_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.spoqahansansneo_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.spoqahansansneo_regular, FontWeight.Normal, FontStyle.Normal)
)


internal val Typography = WorkAlone(
    Heading01 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 32.sp
    ),
    Heading02 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 28.sp
    ),
    Heading03 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 26.sp
    ),
    Heading04 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 24.sp
    ),
    Heading05 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 22.sp
    ),
    Title01 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 24.sp
    ),
    Title02 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 22.sp
    ),
    Body01 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 24.sp
    ),
    Body02 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 22.sp
    ),
    Body03 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 21.sp
    ),
    Body04 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = (-0.5).sp,
        lineHeight = 21.sp
    ),
    Caption01 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        lineHeight = 18.sp
    ),
    Caption02 = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    ),
    XXL01 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        letterSpacing = 0.sp,
        lineHeight = 36.sp
    ),
    XL01 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        letterSpacing = 0.sp,
        lineHeight = 28.sp
    ),
    XL02 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    ),
    L01 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        lineHeight = 24.sp
    ),
    L02 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    ),
    L03 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    ),
    L04 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        lineHeight = 16.sp
    ),
    M01 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
        lineHeight = 14.sp
    ),
    M02 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
        lineHeight = 14.sp
    ),
    S01 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        letterSpacing = 0.sp,
        lineHeight = 13.sp
    ),
    XS01 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        lineHeight = 12.sp
    ),
    XS02 = TextStyle(
        fontFamily = spoqahansans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.sp,
        lineHeight = 12.sp
    ),
)

@Immutable
data class WorkAlone(
    val Heading01: TextStyle,
    val Heading02: TextStyle,
    val Heading03: TextStyle,
    val Heading04: TextStyle,
    val Heading05: TextStyle,
    val Title01: TextStyle,
    val Title02: TextStyle,
    val Body01: TextStyle,
    val Body02: TextStyle,
    val Body03: TextStyle,
    val Body04: TextStyle,
    val Caption01: TextStyle,
    val Caption02: TextStyle,
    val XXL01: TextStyle,
    val XL01: TextStyle,
    val XL02: TextStyle,
    val L01: TextStyle,
    val L02: TextStyle,
    val L03: TextStyle,
    val L04: TextStyle,
    val M01: TextStyle,
    val M02: TextStyle,
    val S01: TextStyle,
    val XS01: TextStyle,
    val XS02: TextStyle
)

val LocalWorkAloneTypography = staticCompositionLocalOf {
    WorkAlone(
        Heading01 = TextStyle.Default,
        Heading02 = TextStyle.Default,
        Heading03 = TextStyle.Default,
        Heading04 = TextStyle.Default,
        Heading05 = TextStyle.Default,
        Title01 = TextStyle.Default,
        Title02 = TextStyle.Default,
        Body01 = TextStyle.Default,
        Body02 = TextStyle.Default,
        Body03 = TextStyle.Default,
        Body04 = TextStyle.Default,
        Caption01 = TextStyle.Default,
        Caption02 = TextStyle.Default,
        // 여기 부터 숫자
        XXL01 = TextStyle.Default,
        XL01 = TextStyle.Default,
        XL02 = TextStyle.Default,
        L01 = TextStyle.Default,
        L02 = TextStyle.Default,
        L03 = TextStyle.Default,
        L04 = TextStyle.Default,
        M01 = TextStyle.Default,
        M02 = TextStyle.Default,
        S01 = TextStyle.Default,
        XS01 = TextStyle.Default,
        XS02 = TextStyle.Default
    )
}

@Composable
fun WorkAloneTheme(
    typography: WorkAlone = Typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalWorkAloneTypography provides typography) {
        content()
    }
}
