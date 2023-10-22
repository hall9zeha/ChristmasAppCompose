package com.barryzea.christmasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


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
import com.barryzea.christmasapp.ui.screens.ReminderDetail
import com.barryzea.christmasapp.ui.screens.RemindersScreen
import com.barryzea.christmasapp.ui.screens.SettingsScreen
import com.barryzea.christmasapp.ui.theme.ChristmasAppTheme
import com.barryzea.christmasapp.ui.theme.blackHard
import com.barryzea.christmasapp.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStore: SettingsStore

    val viewModel:MainViewModel by viewModels()

    private var navController: NavHostController? = null
    private lateinit var scrollState: ScrollState
    private lateinit var  stateScrollList:LazyStaggeredGridState
    private lateinit var detailScreenIsShow:MutableState<Boolean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.loading.value
            }
        }
        setContent {

            navController = rememberNavController()
            scrollState = rememberScrollState()
            stateScrollList = rememberLazyStaggeredGridState()
            val scrollUpState = viewModel.scrollUp.observeAsState()
            viewModel.updateScrollPosition(stateScrollList.firstVisibleItemIndex)
            detailScreenIsShow = rememberSaveable{ mutableStateOf(true) }

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
                                if(scrollState.value ==0 && !scrollUpState.value!!){
                                    BottomNavigationBar(navController = navController!!)
                                }
                            }
                            ) { paddingValues ->
                                SetUpNavController(scrollState,stateScrollList)
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
    fun SetUpNavController(scrollState: ScrollState, scrollListState: LazyStaggeredGridState) {
        NavHost(navController = navController!!, startDestination = Routes.CountDownScreen.route) {
            composable(Routes.CountDownScreen.route) { CountdownScreen(scrollState = scrollState) }
            composable(Routes.SettingsScreen.route) { SettingsScreen(scrollState = scrollState) }
            composable(Routes.RemindersScreen.route){ RemindersScreen(scrollState=scrollListState, navController!!)}
            composable(Routes.ReminderDetail.route){ ReminderDetail()}

        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val screens = listOf(Routes.CountDownScreen.route,Routes.RemindersScreen.route, Routes.SettingsScreen.route)
        val navBackStackEntry by navController?.currentBackStackEntryAsState()!!
        val currentRoute = navBackStackEntry?.destination?.route
        when(currentRoute){
            Routes.CountDownScreen.route->detailScreenIsShow.value=true
            Routes.RemindersScreen.route->detailScreenIsShow.value=true
            Routes.SettingsScreen.route->detailScreenIsShow.value=true
            Routes.ReminderDetail.route->detailScreenIsShow.value=false
        }
        AnimatedVisibility(
            visible = detailScreenIsShow.value,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
        ) {
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
}
private fun getIcoForScreen(screenName:String): Int {
    return when(screenName){
        "Home"-> R.drawable.ic_santa
        "Reminders"->R.drawable.ic_box_gift
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