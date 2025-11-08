package com.example.littlelemon

import android.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.littlelemon.utils.loadImageFromAssets



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(onRegisterSuccess: () -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { HeaderLogoTitle() }
            )
        }
    ) { inner ->
        // Your login form content goes here
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF495E57))
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Lets get to know you better",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Enter personal Information",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 9.dp))

            val context = LocalContext.current
            var firstName by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var message by remember { mutableStateOf("") }

            OutlinedTextField(
                value = firstName,
                onValueChange = {firstName = it},
                label = {Text("enter first name")},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = {lastName = it},
                label = {Text("enter last name")},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = {email = it},
                label = {Text("enter email")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = {
                    if(firstName.isBlank()||lastName.isBlank()||email.isBlank()){
                        message = "Registration Unsuccessful. Please enter the details"
                    }
                    else{
                        saveUserData(context,firstName,lastName,email)
                        message="Registration successful"
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14))
                ){
                Text(text = "Register here",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
                }
            Spacer(modifier = Modifier.height(16.dp))
            if(message.isNotEmpty()){
                Text(
                    text = message,
                    color = if (message.startsWith("Registration successful")) Color(0xFF2E7D32)
                    else Color(0XFFD32F2F),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            // TODO: Username/Password fields etc.
        }
    }

}

@Composable
fun HeaderLogoTitle(
    logoFileName: String = "logo.png",
    appName: String = "Little Lemon"
) {
    val context = LocalContext.current
    // Load once and remember (small file is OK to decode on composition)
    val imageBitmap by remember(context, logoFileName) {
        mutableStateOf(loadImageFromAssets(context, logoFileName))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap!!,
                contentDescription = "App logo",
                modifier = Modifier
                    .size(32.dp)        // small header size
                    .padding(end = 8.dp),


            )
        }
        Text(
            text = appName,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


private fun saveUserLoginStatus(context: Context) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    sharedPref.edit().putBoolean("isLoggedIn", true).apply()
}

fun saveUserData(context: Context, first: String, last: String, email: String){
    val prefs= context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    prefs.edit()
        .putBoolean("isLoggedIn", true)
        .putString("firstName", first)
        .putString("lastName", last)
        .putString("email", email)
        .apply()
}