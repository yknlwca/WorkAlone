package com.ssafy.workalone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.workalone.ui.theme.WalkOneBlue500
import com.ssafy.workalone.ui.theme.WalkOneBlueGray300
import com.ssafy.workalone.ui.theme.WorkAloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkAloneTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column {
                        Text(
                            text = "타이포그래피",
                            style = WorkAloneTheme.typography.Heading01
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "타이포그래피",
                            style = WorkAloneTheme.typography.Heading02,
                            color = WalkOneBlue500
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "타이포그래피",
                            style = WorkAloneTheme.typography.Body02
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "1234567890",
                            style = WorkAloneTheme.typography.XXL01,
                            color = WalkOneBlueGray300
                        )
                        Text(
                            text = "1234567890",
                            style = WorkAloneTheme.typography.XS02
                        )
                    }
                }
            }
        }
    }
}
