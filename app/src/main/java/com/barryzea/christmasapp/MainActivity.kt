package com.barryzea.christmasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.barryzea.christmasapp.ui.components.CountdownScreen
import com.barryzea.christmasapp.ui.theme.ChristmasAppTheme
import com.barryzea.christmasapp.ui.theme.salmonRed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChristmasAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,

                ) {
                    Card(modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                        elevation = CardDefaults.cardElevation( defaultElevation = 4.dp),
                        shape= RoundedCornerShape(16.dp)
                    ){
                    Scaffold(bottomBar = { BottomNavigationBar()}) {paddingValues->
                           CountdownScreen()
                           //para evitar que el Scaffold se queje por no usar sus paddingValues usaremos lo siguiente
                            Column (modifier=Modifier.padding(paddingValues)){}
                        }

                    }

                }
            }
        }
    }
}
@Composable
fun BottomNavigationBar(){
    val screens = listOf("Home","Settings")
    var selectedScreen by remember { mutableStateOf(screens.first()) }
    NavigationBar(containerColor = Color(0xfff9f6f9)){
        screens.forEach { screen->
            NavigationBarItem(selected = screen ==selectedScreen,
                modifier= Modifier.padding(8.dp),
                onClick = {  },
                icon ={ Icon(painterResource(getIcoForScreen(screenName = screen)) ,contentDescription="") })

        }
    }

}
fun getIcoForScreen(screenName:String): Int {
    return when(screenName){
        "Home"-> R.drawable.ic_santa
        "Settings" -> R.drawable.ic_snowflake
        else -> R.drawable.ic_tree
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChristmasAppTheme {

    }
}