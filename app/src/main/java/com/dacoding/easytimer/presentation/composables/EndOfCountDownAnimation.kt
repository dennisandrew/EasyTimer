package com.dacoding.easytimer.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape

@Composable
fun EndOfCountDownAnimation() {

    var konfettiWidth by remember { mutableStateOf(0) }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { layoutInfo ->
                konfettiWidth = layoutInfo.size.width
            },
        factory = { ctx ->
            KonfettiView(context = ctx)
        },
        update = { konfetti ->
            konfetti.build()
                .addColors(
                    android.graphics.Color.BLUE,
                    android.graphics.Color.CYAN,
                    android.graphics.Color.MAGENTA
                )
                .setDirection(0.0, 359.9)
                .setSpeed(2f, 7f)
                .setFadeOutEnabled(true)
                .setTimeToLive(5000L)
                .addShapes(Shape.Circle)
                .addSizes(nl.dionsegijn.konfetti.models.Size(8))
                .setPosition(-50f, konfettiWidth.toFloat(), -50f)
                .streamFor(100, 10000L)
        }
    )
}