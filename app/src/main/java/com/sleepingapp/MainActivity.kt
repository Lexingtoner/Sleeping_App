package com.sleepingapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SleepApp() }
    }
}

enum class SleepRoute(val route: String) {
    Home("home"), Chat("chat"), CheckIn("checkin"), Stats("stats"), Alarm("alarm"), Sounds("sounds")
}

private data class SleepCard(val title: String, val subtitle: String)
private data class ChatMessage(val fromUser: Boolean, val text: String)
private data class QuickActivity(val title: String, val activityClass: Class<out ComponentActivity>)
private data class BottomTab(val route: SleepRoute, val label: String, val icon: ImageVector)

@Composable
fun SleepApp() {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold(
            containerColor = Color(0xFF040B22),
            bottomBar = { SleepBottomBar(navController) }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHost(navController = navController, startDestination = SleepRoute.Home.route) {
                    composable(SleepRoute.Home.route) { HomeScreen(navController) }
                    composable(SleepRoute.Chat.route) { ChatScreen(navController) }
                    composable(SleepRoute.CheckIn.route) { CheckInScreen(navController) }
                    composable(SleepRoute.Stats.route) { StatsScreen(navController) }
                    composable(SleepRoute.Alarm.route) { AlarmScreen(navController) }
                    composable(SleepRoute.Sounds.route) { SoundsScreen(navController) }
                }
            }
        }
    }
}

@Composable
private fun SleepBottomBar(navController: NavHostController) {
    val tabs = listOf(
        BottomTab(SleepRoute.Home, "Home", Icons.Default.Home),
        BottomTab(SleepRoute.Stats, "Stats", Icons.Default.Alarm),
        BottomTab(SleepRoute.Sounds, "Sounds", Icons.Default.LibraryMusic),
        BottomTab(SleepRoute.Chat, "Luna", Icons.Default.Chat)
    )
    val entry by navController.currentBackStackEntryAsState()
    val current = entry?.destination?.route

    NavigationBar(containerColor = Color(0xFF08142F)) {
        tabs.forEach { tab ->
            NavigationBarItem(
                selected = current == tab.route.route,
                onClick = {
                    navController.navigate(tab.route.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(SleepRoute.Home.route) { saveState = true }
                    }
                },
                icon = { Icon(tab.icon, contentDescription = tab.label) },
                label = { Text(tab.label) }
            )
        }
    }
}

@Composable
private fun GradientBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF061235), Color(0xFF050A1D), Color(0xFF030610))
                )
            )
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
private fun HomeScreen(navController: NavHostController) {
    val prepareCards = listOf(
        SleepCard("4-7-8 Breathing", "2 min"),
        SleepCard("Rain on Window", "Sound"),
        SleepCard("Wind Down Story", "10 min")
    )
    val quickActivities = listOf(
        QuickActivity("Breathing", BreathingActivity::class.java),
        QuickActivity("Meditation", MeditationActivity::class.java),
        QuickActivity("Tips", SleepTipsActivity::class.java),
        QuickActivity("Profile", ProfileActivity::class.java)
    )

    GradientBackground {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Good Evening", color = Color(0xFFAAB6D6))
                        Text("Alex", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = { navController.navigate(SleepRoute.Alarm.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
                    }
                }
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(quickActivities) { qa ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF172444)),
                            modifier = Modifier.clickable {
                                navController.context.startActivity(Intent(navController.context, qa.activityClass))
                            }
                        ) {
                            Text(qa.title, color = Color.White, modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp))
                        }
                    }
                }
            }
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF101A3D)),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(230.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF12244E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("6h 30m", color = Color.White, fontSize = 46.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { navController.navigate(SleepRoute.CheckIn.route) }, modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Default.PlayArrow, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Start Sleep Tracking")
                        }
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    MetricCard("Sleep Score", "85", "+2%", Modifier.weight(1f))
                    MetricCard("Hours Slept", "7h 12m", "Avg", Modifier.weight(1f))
                }
            }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF12203F)), modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Luna suggestion: your stress is medium tonight. Try Breathing + Rain sounds for 15 minutes.",
                        color = Color(0xFFB7C6EA),
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Prepare for rest", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Text("See all", color = Color(0xFF2D75FF), modifier = Modifier.clickable { navController.navigate(SleepRoute.Sounds.route) })
                }
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(prepareCards) { card ->
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2647)), modifier = Modifier.width(200.dp)) {
                            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(card.title, color = Color.White, fontWeight = FontWeight.SemiBold)
                                Text(card.subtitle, color = Color(0xFF95A7D6))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, hint: String, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF131E3D)), modifier = modifier) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, color = Color(0xFF8FA0C9), fontSize = 12.sp)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Text(hint, color = Color(0xFF34D3A1))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(navController: NavHostController) {
    var text by rememberSaveable { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMessage(false, "Good evening. Your resting heart rate was slightly elevated. Let's try a calming breathing exercise."),
            ChatMessage(true, "Yes, I'm feeling a bit restless. My mind won't stop racing."),
            ChatMessage(false, "I understand. Let's try the 4-7-8 pattern for two minutes.")
        )
    }

    GradientBackground {
        Column {
            ScreenHeader(title = "Luna", subtitle = "CALM PRESENCE", onBack = { navController.navigate(SleepRoute.Home.route) })

            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(messages) { msg ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (msg.fromUser) Arrangement.End else Arrangement.Start) {
                        Card(colors = CardDefaults.cardColors(containerColor = if (msg.fromUser) Color(0xFF2261F0) else Color(0xFF1B2847))) {
                            Text(msg.text, color = Color.White, modifier = Modifier.padding(14.dp))
                        }
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Tell Luna how you feel...") }
                )
                IconButton(onClick = {
                    if (text.isNotBlank()) {
                        messages.add(ChatMessage(true, text))
                        messages.add(ChatMessage(false, "Спасибо. Сделай длинный выдох и расслабь плечи."))
                        text = ""
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = null, tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun CheckInScreen(navController: NavHostController) {
    val factors = listOf("Caffeine", "Alcohol", "Heavy Meal", "Exercise", "Screen Time", "Meditation")
    val selected = remember { mutableStateListOf("Caffeine") }
    var stress by rememberSaveable { mutableIntStateOf(5) }
    var notes by rememberSaveable { mutableStateOf("") }

    GradientBackground {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                ScreenHeader(title = "How was your day?", subtitle = "Select factors that might affect your sleep tonight.", onBack = {
                    navController.navigate(SleepRoute.Home.route)
                })
            }
            item {
                factors.chunked(2).forEach { rowItems ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        rowItems.forEach { factor ->
                            val isSelected = factor in selected
                            Card(
                                colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFF1F4CC7) else Color(0xFF18233F)),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(90.dp)
                                    .clickable { if (isSelected) selected.remove(factor) else selected.add(factor) }
                            ) {
                                Box(Modifier.fillMaxSize().padding(12.dp), contentAlignment = Alignment.CenterStart) {
                                    Text(factor, color = Color.White)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
            item {
                Text("Stress Level: $stress/10", color = Color.White, fontWeight = FontWeight.SemiBold)
                Slider(value = stress.toFloat(), onValueChange = { stress = it.toInt() }, valueRange = 1f..10f)
            }
            item {
                OutlinedTextField(value = notes, onValueChange = { notes = it }, modifier = Modifier.fillMaxWidth().height(120.dp), placeholder = { Text("Clear your mind here...") })
            }
            item {
                Button(onClick = { navController.navigate(SleepRoute.Home.route) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Save & Sleep")
                }
            }
        }
    }
}

@Composable
private fun StatsScreen(navController: NavHostController) {
    GradientBackground {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { ScreenHeader(title = "Sleep Statistics", subtitle = "This week", onBack = { navController.navigate(SleepRoute.Home.route) }) }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF111D3B)), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("85", fontSize = 72.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Great Sleep", color = Color(0xFF95A5CE))
                    }
                }
            }
            item {
                Text("Sleep Cycles", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                    repeat(7) { idx ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .height((70 + idx % 3 * 20).dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF2B73FF))
                        ) {}
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    MetricCard("Avg Duration", "7h 42m", "Target 8h", Modifier.weight(1f))
                    MetricCard("Efficiency", "92%", "Optimal", Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun AlarmScreen(navController: NavHostController) {
    var smartWake by rememberSaveable { mutableStateOf(true) }
    var selected by rememberSaveable { mutableStateOf(setOf("M", "T", "W", "T2", "F")) }

    GradientBackground {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { ScreenHeader(title = "Edit Alarm", subtitle = "Save", onBack = { navController.navigate(SleepRoute.Home.route) }) }
            item {
                Text("07:30", color = Color(0xFFE9BC4A), fontSize = 86.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                Text("Alarm in 7h 30m", color = Color(0xFF8DA0CB), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2646)), modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Smart Wake-up", color = Color.White, fontWeight = FontWeight.Bold)
                            Text("Wake up gently within 30 min window", color = Color(0xFF93A7D3))
                        }
                        Switch(checked = smartWake, onCheckedChange = { smartWake = it })
                    }
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    listOf("M", "T", "W", "T2", "F", "S", "S2").forEach { day ->
                        val active = day in selected
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(if (active) Color(0xFF2A73FF) else Color(0xFF1A2545))
                                .clickable { selected = if (active) selected - day else selected + day },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(day.take(1), color = Color.White)
                        }
                    }
                }
            }
            item {
                Button(onClick = { navController.navigate(SleepRoute.Home.route) }, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Alarm, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Enable Alarm")
                }
            }
        }
    }
}

@Composable
private fun SoundsScreen(navController: NavHostController) {
    val recent = listOf("Heavy Rain", "Deep Sleep", "Forest Night")
    val categories = listOf("Rain", "Forest", "White Noise", "Meditations")
    var query by rememberSaveable { mutableStateOf("") }

    val filteredRecent = recent.filter { it.contains(query, ignoreCase = true) || query.isBlank() }

    GradientBackground {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { ScreenHeader(title = "Sound Library", subtitle = "Find your calm", onBack = { navController.navigate(SleepRoute.Home.route) }) }
            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("Search sounds, stories...") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item { Text("Recently Played", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold) }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(filteredRecent) { item ->
                        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF172443)), modifier = Modifier.width(180.dp)) {
                            Column(Modifier.padding(14.dp)) {
                                Text(item, color = Color.White, fontWeight = FontWeight.SemiBold)
                                Text("20-60 min", color = Color(0xFF8BA0CD))
                            }
                        }
                    }
                }
            }
            item { Text("Categories", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold) }
            item {
                categories.chunked(2).forEach { rowItems ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        rowItems.forEach { category ->
                            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2646)), modifier = Modifier.weight(1f).height(110.dp)) {
                                Box(Modifier.fillMaxSize().padding(12.dp), contentAlignment = Alignment.BottomStart) {
                                    Text(category, color = Color.White, fontSize = 22.sp)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun ScreenHeader(title: String, subtitle: String, onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Text(subtitle, color = Color(0xFFA5B4DA))
        }
        Spacer(Modifier.width(48.dp))
    }
}
