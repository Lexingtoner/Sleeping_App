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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ProfileScreen(onBack = { finish() })
            }
        }
    }
}

@Composable
private fun ProfileScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF061235), Color(0xFF050A1D))))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
            }
            Text("Profile", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 28.sp)
        }

        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF162241)), shape = RoundedCornerShape(20.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Alex", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text("Sleep Goal: 8h 00m", color = Color(0xFF9CB2DF))
                Text("Wake-up Goal: 07:00", color = Color(0xFF9CB2DF))
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF162241))) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Weekly Progress", color = Color.White, fontWeight = FontWeight.SemiBold)
                Text("Average Sleep: 7h 42m", color = Color(0xFF9CB2DF))
                Text("Sleep Score: 85", color = Color(0xFF9CB2DF))
                Text("Consistency: Good", color = Color(0xFF53D39A))
            }
        }
    }
}
