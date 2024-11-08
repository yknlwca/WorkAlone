package com.ssafy.workalone.presentation.ui.component.dialog

import android.widget.NumberPicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WeightPickerDialog(
    initialWeight: Int = 70,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedWeight by remember { mutableStateOf(initialWeight) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("몸무게 선택") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AndroidView(
                    factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 30  // 최소 몸무게
                            maxValue = 200 // 최대 몸무게
                            value = selectedWeight
                            setOnValueChangedListener { _, _, newVal ->
                                selectedWeight = newVal
                            }
                        }
                    },
                    modifier = Modifier.wrapContentSize()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(selectedWeight)
            }) {
                Text("확인")
            }
        },
        containerColor = Color.White,
        textContentColor = Color.Black
    )
}
