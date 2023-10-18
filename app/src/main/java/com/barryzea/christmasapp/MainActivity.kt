package com.barryzea.christmasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.barryzea.christmasapp.common.Routes
import com.barryzea.christmasapp.common.preferences.SettingsStore
import com.barryzea.christmasapp.data.model.DarkTheme
import com.barryzea.christmasapp.data.model.PrefsEntity
import com.barryzea.christmasapp.data.model.localTheme
import com.barryzea.christmasapp.ui.screens.CountdownScreen
import com.barryzea.christmasapp.ui.screens.SettingsScreen
import com.barryzea.christmasapp.ui.theme.ChristmasAppTheme
import com.barryzea.christmasapp.ui.theme.blackHard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStore: SettingsStore
    private var navController: NavHostController? = null
    private lateinit var scrollState: ScrollState
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            scrollState = rememberScrollState()
            //obtenemos el valor booleano para el tema en  general, guardado en Data Store que por defecto es false
            val isDarkTheme =
                dataStore.getFromDataStore().collectAsState(PrefsEntity(false)).value.darkTheme
            val darkTheme = DarkTheme(isDarkTheme!!)
            //lo guardamos en el LocalProvider para que sea accecible en toda la app sin tener que llamar las
            //preferencias nuevamente
            CompositionLocalProvider(localTheme provides darkTheme) {
                ChristmasAppTheme(darkTheme = localTheme.current.isDark) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Scaffold(bottomBar = {
                                if (scrollState.value == 0) {
                                    BottomNavigationBar(navController!!)
                                }
                            }
                            ) { paddingValues ->
                                SetUpNavController(scrollState)
                                //para evitar que el Scaffold se queje por no usar sus paddingValues usaremos lo siguiente
                                Column(modifier = Modifier.padding(paddingValues)) {}
                            }
                        }

                    }

                }
            }
        }
    }

    @Composable
    fun SetUpNavController(scrollState: ScrollState) {
        NavHost(navController = navController!!, startDestination = Routes.CountDownScreen.route) {
            composable(Routes.CountDownScreen.route) { CountdownScreen(scrollState = scrollState) }
            composable(Routes.SettingsScreen.route) { SettingsScreen(scrollState = scrollState) }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val screens = listOf(Routes.CountDownScreen.route, Routes.SettingsScreen.route)
        val navBackStackEntry by navController?.currentBackStackEntryAsState()!!
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBar(
            containerColor = if (localTheme.current.isDark) blackHard else
                Color(0xfff9f6f9)
        ) {
            screens.forEach { screen ->
                NavigationBarItem(selected = currentRoute == screen,
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        navController?.navigate(screen) {
                            navController?.graph?.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(getIcoForScreen(screenName = screen)),
                            contentDescription = ""
                        )
                    })
            }
        }
    }
}
private fun getIcoForScreen(screenName:String): Int {
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