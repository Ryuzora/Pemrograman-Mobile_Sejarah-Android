package com.example.mobilerillnih

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobilerillnih.ui.theme.MobilerillnihTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobilerillnihTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "page1",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("page1"){
                            Greeting(onNextClick = {navController.navigate("page2")})
                        }

                        composable("page2"){
                            SecondPage(onBackClick = {navController.navigate("page1")})
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, onNextClick: () -> Unit) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Sejarah Android",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        HorizontalDivider(
            thickness = 2.dp,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 16.dp)
        )


        Text(
            text = """Didirikan pada tahun 2003 oleh Andy Rubin, Rich Miner, Nick Sears, dan Chris White, Android Inc. awalnya dikembangkan sebagai sistem operasi canggih untuk kamera digital. Namun, mereka segera menyadari potensi pasar yang lebih besar di perangkat seluler dan memutuskan untuk bersaing dengan pemain besar saat itu seperti Symbian dan Windows Mobile.

Pada tahun 2005, Google mengakuisisi Android Inc. dan mulai mengembangkannya menggunakan kernel Linux. Langkah strategis berlanjut dengan pembentukan Open Handset Alliance pada 2007, yang akhirnya melahirkan perangkat komersial pertama, T-Mobile G1 (HTC Dream) pada 2008. Perangkat ini memperkenalkan fondasi Android modern, termasuk integrasi layanan Google yang kuat.

Selama dekade berikutnya, Android berevolusi pesat melalui sistem penamaan versi berdasarkan makanan manis dan sifatnya yang open-source. Fleksibilitas ini memungkinkan berbagai produsen ponsel di seluruh dunia mengadopsi Android, menjadikannya sistem operasi seluler yang paling banyak digunakan di dunia saat ini, menjangkau miliaran pengguna di berbagai segmen ekonomi.""".trimIndent(),

            )

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}

data class DeviceProperty(val label: String, val value: String)

@SuppressLint("ServiceCast")
@Composable
fun SecondPage(onBackClick: () -> Unit){
    Column(modifier = Modifier.padding(16.dp)){
        Text(
            text = "Arsitektur Android",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium
        )

        val architectures = remember { listOf(
            "System Apps",
            "Java API Framerwork",
            "Native C/C++ Libraries & Android Runtime",
            "Hardware Abstraction Layer",
            "Linux Kernel"
        )}

        Column {
            architectures.forEach { item ->
                Card(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Green,
                        contentColor = Color.Black
                    )
                ){
                    Text(text = item, modifier = Modifier.padding(start = 12.dp).padding(vertical = 8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        val context = LocalContext.current
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isWifi = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        val isCellular = capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
        val connectionType = when {
            isWifi -> "WiFi"
            isCellular -> "Cellular"
            else -> "No Connection"
        }

        val deviceInfo = remember {
            listOf(
                DeviceProperty("Brand", android.os.Build.BRAND),
                DeviceProperty("Model", android.os.Build.MODEL),
                DeviceProperty("Manufacturer", android.os.Build.MANUFACTURER),
                DeviceProperty("Android Version", android.os.Build.VERSION.RELEASE),
                DeviceProperty("Kernel Version", System.getProperty("os.version") ?: "Unknown"),
                DeviceProperty("Battery Level", "$batteryLevel%"),
                DeviceProperty("Connection Type", connectionType)
            )
        }

        Text(
            text = "Device Information",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineMedium

        )

        LazyColumn {
            items(deviceInfo) { item ->
                Card(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(start = 12.dp)) {
                        Text(text = item.label + ": ")
                        Text(text = item.value)
                    }
                }
            }
        }

        Button(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
        ){
            Text("Back")
        }
    }
}
