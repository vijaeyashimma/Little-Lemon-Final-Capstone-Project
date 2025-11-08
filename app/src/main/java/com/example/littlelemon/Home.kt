package com.example.littlelemon

import androidx.compose.foundation.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.ImageBitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import com.example.littlelemon.utils.loadImageFromAssets
import androidx.room.Room
import androidx.compose.foundation.background
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.material3.TextFieldDefaults
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@Composable
fun Home(onProfileClick: () -> Unit) {
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(context, AppDatabase::class.java, "littlelemon.db").build()
    }
    val menuDao = db.menuDao()
    val menuItems by menuDao.getAllMenuItems().collectAsState(initial = emptyList())

    var searchPhrase by remember { mutableStateOf("") }

    val filteredItems = remember(searchPhrase, menuItems) {
        if (searchPhrase.isNotBlank()) {
            menuItems.filter {
                it.title.contains(searchPhrase, ignoreCase = true) ||
                        it.description.contains(searchPhrase, ignoreCase = true) ||
                        it.category.contains(searchPhrase, ignoreCase = true)
            }
        } else {
            menuItems
        }
    }


    Scaffold(
        topBar = {
            HomeHeader(
                onProfileClick = onProfileClick,
                logoFileName = "logo.png",
                profileFileName = "Profile.png"
            )
        }
    )
    { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(Color.White)
        ) {
            HeroSection(searchPhrase = searchPhrase, onSearchChange = { searchPhrase = it })
            MenuHeader()
            MenuItems(filteredItems)
        }
    }
    // Placeholder
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(onProfileClick: () -> Unit, logoFileName: String, profileFileName: String) {
    val context = LocalContext.current
    val logoBitmap = remember { loadImageFromAssets(context, logoFileName) }
    val profileBitmap = remember { loadImageFromAssets(context, profileFileName) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    logoBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "App logo",
                            modifier = Modifier
                                .size(36.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    Text(
                        text = "Little Lemon",
                        style = MaterialTheme.typography.titleMedium
                    )
            }
                profileBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onProfileClick() }
                    )
                }
            }
        }
    )
}

@Composable
fun HeroSection(searchPhrase: String,
                onSearchChange: (String) -> Unit) {
    val context = LocalContext.current
    val heroBitmap = loadImageFromAssets(context, "hero_image.png")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF495E57))
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Little Lemon",
                    color = Color(0xFFF4CE14),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Chicago",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }

            heroBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Hero image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = searchPhrase,
            onValueChange = onSearchChange,
            placeholder = { Text("Enter search phrase") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
        )


    }
}

@Composable
fun MenuHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    ) {
        Text(
            text = "ORDER FOR DELIVERY!",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 12.dp)
        ) {
            val categories = listOf("Starters", "Mains", "Desserts", "Drinks")
            categories.forEach {
                Surface(
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItems(items: List<MenuItemEntity>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        items.forEach { item ->
            MenuItemCard(item)
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(item: MenuItemEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Text(item.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                item.description,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "$${item.price}",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF495E57)
            )
        }

        GlideImage(
            model = item.image,
            contentDescription = item.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}



