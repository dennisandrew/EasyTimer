package com.dacoding.easytimer.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dacoding.easytimer.presentation.composables.CountDownButton
import com.dacoding.easytimer.presentation.composables.CountDownIndicator
import com.dacoding.easytimer.presentation.composables.EndOfCountDownAnimation
import com.dacoding.easytimer.presentation.composables.TimePicker
import com.dacoding.easytimer.viewmodel.MainViewModel

@ExperimentalMaterial3Api
@Composable
fun CountDownScreen(viewModel: MainViewModel) {

    val time by viewModel.time.observeAsState(viewModel.time.value)
    val progress by viewModel.progress.observeAsState(1.00F)
    val isPlaying by viewModel.isPlaying.observeAsState(false)
    val celebrate by viewModel.celebrate.observeAsState(false)







    CountDown(
        viewModel = viewModel,
        time = if (viewModel.pickedTime.value == null) {
            "00:00"
        } else {
            time!!
        },
        progress = progress,
        isPlaying = isPlaying,
        celebrate = celebrate
    ) {
        viewModel.handleCountDownTimer()
    }


}

@ExperimentalMaterial3Api
@Composable
fun CountDown(
    viewModel: MainViewModel,
    time: String,
    progress: Float,
    isPlaying: Boolean,
    celebrate: Boolean,
    optionSelected: () -> Unit
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {

        if (celebrate) {
            EndOfCountDownAnimation(viewModel = viewModel)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                text = "${viewModel.time.value} to launch...",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            )

            Text(
                text = "Click to start or stop countdown",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            CountDownIndicator(
                Modifier.padding(top = 50.dp),
                progress = progress,
                time = time,
                size = 250,
                stroke = 12
            )

            Spacer(modifier = Modifier.height(24.dp))

            TimePicker(viewModel = viewModel)

            Spacer(modifier = Modifier.height(24.dp))

            CountDownButton(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .size(70.dp),
                isPlaying = isPlaying
            ) {
                optionSelected()

            }
        }
    }
}





