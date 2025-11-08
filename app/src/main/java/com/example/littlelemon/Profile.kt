package com.example.littlelemon
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import android.content.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(onBackClick: () -> Unit, onLogoutClick: () -> Unit) {
    val context = LocalContext.current
    val (firstName, lastName, email) = loadUserData(context)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { HeaderLogoTitle() }
            )
        }
    ){ inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(24.dp))
            // Section Title
            Text(
                text = "Profile information:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp)
            )
            // --- Display stored data ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                Text("First Name: $firstName", fontSize = 16.sp, color = Color(0xFF333333))
                Text("Last Name: $lastName", fontSize = 16.sp, color = Color(0xFF333333))
                Text("Email: $email", fontSize = 16.sp, color = Color(0xFF333333))
            }

            // --- Buttons ---
            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
            ) {
                Text("Logout", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

    }
}

fun loadUserData(context: Context): Triple<String, String, String> {
    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val first = prefs.getString("firstName", "") ?: ""
    val last = prefs.getString("lastName", "") ?: ""
    val email = prefs.getString("email", "") ?: ""
    return Triple(first, last, email)
}