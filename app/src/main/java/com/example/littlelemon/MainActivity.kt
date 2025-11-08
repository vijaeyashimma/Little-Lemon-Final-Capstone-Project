package com.example.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.littlelemon.ui.theme.LittlelemonTheme
import com.example.littlelemon.Onboarding
import androidx.navigation.compose.rememberNavController
import littlelemon.MenuRepository
import androidx.lifecycle.lifecycleScope
import kotlinx. coroutines. launch
import androidx.room.Room
import androidx.compose.runtime.remember


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Step 1: Initialize database
        val db = DatabaseProvider.getDatabase(applicationContext)

        // ✅ Step 2: Create repository
        val repository = MenuRepository(db.menuDao())

        // ✅ Step 3: Fetch and save menu data
        lifecycleScope.launch {
            repository.refreshMenu()
        }

        // ✅ Step 4: Set up UI
        setContent {
            val navController = rememberNavController()
            MyNavigation(navController = navController)
        }
    }
}




