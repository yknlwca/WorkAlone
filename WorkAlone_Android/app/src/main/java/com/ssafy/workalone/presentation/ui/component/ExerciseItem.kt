package com.ssafy.workalone.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssafy.workalone.R
import com.ssafy.workalone.data.model.Challenge
import com.ssafy.workalone.presentation.ui.theme.LocalWorkAloneTypography
import com.ssafy.workalone.presentation.ui.theme.WalkOneBlue500
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray50
import com.ssafy.workalone.presentation.ui.theme.WalkOneGray700
import com.ssafy.workalone.presentation.ui.theme.WorkAloneTheme

@Composable
fun ExerciseItem(challenge: Challenge, onClick: () -> Unit) {
    WorkAloneTheme {
        val typography = LocalWorkAloneTypography.current
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickable { onClick() },
            elevation = 8.dp,
            backgroundColor = WalkOneGray50,
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(WalkOneGray50)
                ) {
                    val imageResId = R.drawable.w_logo
//                    if (challenge.exerciseType.equals("SQUAT")) {
//                        imageResId = R.drawable.squat
//                    } else if (exercise.exerciseType.equals("PUSHUP")) {
//                        imageResId = R.drawable.push_up
//                    } else if (exercise.exerciseType.equals("SITUP")) {
//                        imageResId = R.drawable.sit_up
//                    } else if (exercise.exerciseType.equals("PLANK")) {
//                        imageResId = R.drawable.plank
//                    }
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Exercise Image",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = challenge.title,
                        style = typography.Heading02,
                        color = WalkOneBlue500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        if (challenge.exerciseRepeat != null && challenge.exerciseSet != null) {
                            Text(
                                text = "${challenge.exerciseRepeat} 개",
                                style = typography.Body03,
                                color = WalkOneGray700,
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "X",
                                style = typography.Body03,
                                color = WalkOneGray700,
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                text = "${challenge.exerciseSet} 세트",
                                style = typography.Body03,
                                color = WalkOneGray700,
                            )
                        }
                    }
                }
            }
        }
    }
}
