package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {

    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade 🍋",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {

            when (currentStep) {

                // 🍋 Step 1: Select lemon
                1 -> {
                    LemonTextAndImage(
                        text = stringResource(R.string.lemon_select),
                        drawableRes = R.drawable.lemon_tree,
                        contentDesc = stringResource(R.string.lemon_tree_content_description),
                        onClick = {
                            currentStep = 2
                            squeezeCount = (2..4).random()
                        }
                    )
                }

                // 🍋 Step 2: Squeeze lemon
                2 -> {
                    LemonTextAndImage(
                        text = "Squeezes left: $squeezeCount 🍋",
                        drawableRes = R.drawable.lemon_squeeze,
                        contentDesc = stringResource(R.string.lemon_content_description),
                        animate = true,
                        onClick = {
                            squeezeCount--
                            if (squeezeCount == 0) {
                                currentStep = 3
                            }
                        }
                    )
                }

                // 🍹 Step 3: Drink lemonade
                3 -> {
                    LemonTextAndImage(
                        text = stringResource(R.string.lemon_drink),
                        drawableRes = R.drawable.lemon_drink,
                        contentDesc = stringResource(R.string.lemonade_content_description),
                        showCelebration = true,
                        onClick = {
                            currentStep = 4
                        }
                    )
                }

                // 🥤 Step 4: Restart
                4 -> {
                    LemonTextAndImage(
                        text = stringResource(R.string.lemon_empty_glass),
                        drawableRes = R.drawable.lemon_restart,
                        contentDesc = stringResource(R.string.empty_glass_content_description),
                        onClick = {
                            currentStep = 1
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LemonTextAndImage(
    text: String,
    drawableRes: Int,
    contentDesc: String,
    animate: Boolean = false,
    showCelebration: Boolean = false,
    onClick: () -> Unit
) {

    val scale by animateFloatAsState(
        targetValue = if (animate) 1.1f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = ""
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = onClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {

            Image(
                painter = painterResource(drawableRes),
                contentDescription = contentDesc,
                modifier = Modifier
                    .size(200.dp)
                    .scale(scale)
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            fontSize = 18.sp
        )

        if (showCelebration) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Refreshing! 🍹✨",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00C853)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLemonade() {
    LemonadeTheme {
        LemonadeApp()
    }
}