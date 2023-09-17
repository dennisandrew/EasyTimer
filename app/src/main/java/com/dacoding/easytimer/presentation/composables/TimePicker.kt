package com.dacoding.easytimer.presentation.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dacoding.easytimer.viewmodel.MainViewModel

@ExperimentalMaterial3Api
@Composable
fun TimePicker(
    viewModel: MainViewModel
) {

    val context = LocalContext.current

    var pickedTime by remember {
        mutableLongStateOf(0L)
    }

    var time by remember {
        mutableStateOf("")
    }
    TextField(
        value = time,
        onValueChange = {
            time = it
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        maxLines = 1,
        label = { Text(text = "Enter the time to count down in seconds") },
        placeholder = { Text(text = "Time in seconds") }
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        val toast = Toast.makeText(context, "Confirmed", Toast.LENGTH_SHORT)
        pickedTime = time.toLongOrNull()?.times(1000) ?: 0
        viewModel.setPickedTime(pickedTime)
        toast.show()


    }) {
        Text(text = "Confirm")
    }
}
