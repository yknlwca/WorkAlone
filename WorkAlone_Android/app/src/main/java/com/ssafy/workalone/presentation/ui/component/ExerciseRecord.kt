package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.workalone.R
import com.ssafy.workalone.presentation.ui.screen.IntegratedCompleteView
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray100
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray300


@Composable
fun ExerciseRecord(
    imagePainter: Painter,
    titleText: String,
    contentText: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = imagePainter,
                    contentDescription = "Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = titleText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Box {
                Text(
                    text = contentText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}