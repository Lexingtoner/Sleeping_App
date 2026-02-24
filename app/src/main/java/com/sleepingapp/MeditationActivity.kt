package com.sleepingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MeditationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MeditationScreen(onBack = { finish() })
            }
        }
    }
}

private data class MeditationItem(val title: String, val duration: String, val subtitle: String)

@androidx.compose.runtime.Composable
private fun MeditationScreen(onBack: () -> Unit) {
    val items = listOf(
        MeditationItem("Calm Presence", "12 min", "Grounding before sleep"),
        MeditationItem("Body Scan", "15 min", "Release tension from head to toe"),
        MeditationItem("Quiet Mind", "10 min", "For racing thoughts")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF061235), Color(0xFF050A1D))))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
            }
            Text("Meditations", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items) { item ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF182647))) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Text(item.title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                        Text(item.duration, color = Color(0xFF7DB0FF))
                        Text(item.subtitle, color = Color(0xFFA0AFD0))
                    }
                }
            }
        }
    }
}
