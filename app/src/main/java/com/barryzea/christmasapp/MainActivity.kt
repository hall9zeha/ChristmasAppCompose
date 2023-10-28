package com.barryzea.christmasapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.barryzea.christmasapp.common.BottomBarTab
import com.barryzea.christmasapp.common.Routes
import com.barryzea.christmasapp.common.preferences.SettingsStore
import com.barryzea.christmasapp.data.model.DarkTheme
import com.barryzea.christmasapp.data.model.PrefsEntity
import com.barryzea.christmasapp.data.model.localTheme
import com.barryzea.christmasapp.ui.components.rememberAppState
import com.barryzea.christmasapp.ui.navigation.COUNTDOWN_GRAPH
import com.barryzea.christmasapp.ui.navigation.navGraph
import com.barryzea.christmasapp.ui.screens.CountdownScreen
import com.barryzea.christmasapp.ui.screens.ReminderDetail
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

    val viewModel: MainViewModel by viewModels()

    private var navController: NavHostController? = null
    private lateinit var scaffoldScrollState: ScrollState
    private lateinit var detailScreenIsShow: MutableState<Boolean>
    private lateinit var stateScrollList: LazyStaggeredGridState


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.loading.value
            }
        }
        setContent {
            navController = rememberNavController()
            scaffoldScrollState = rememberScrollState()
            stateScrollList = rememberLazyStaggeredGridState()
           /* val scrollUpState by viewModel.scrollUp.observeAsState()
            viewModel.updateScrollPosition(stateScrollList.firstVisibleItemIndex)*/
            detailScreenIsShow = remember { mutableStateOf(true) }

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

                            Container()
                        }

                    }

                }
            }
        }
    }

    @Composable
    fun Container() {
        val appState = rememberAppState()
        Scaffold(
            bottomBar = {
                 if (appState.shouldShowBottomBar) {
                        BottomBar(
                            tabs = appState.bottomBarTabs,
                            currentRoute = appState.currentRoute!!,
                            navigateToRoute = appState::navigateToBottomBarRoute
                        )
                    }
            }) { paddingValues ->

            NavHost(
                navController = appState.navController,
                startDestination = COUNTDOWN_GRAPH,
                modifier = Modifier.padding(paddingValues)
            ) {
                navGraph(
                    onReminderItemSelected = appState::navigateToReminderItemDetail,
                    upPress = appState::upPress
                )
            }
        }

}
@Composable
fun BottomBar(
    tabs: Array<BottomBarTab>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit
) {
    NavigationBar(
        containerColor = if (localTheme.current.isDark) blackHard else
            Color(0xfff9f6f9)
    ) {
        tabs.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                modifier = Modifier.padding(8.dp),
                onClick = {navigateToRoute(item.route)
                },
                icon = {
                    Icon(
                        painterResource(getIcoForScreen(screenName = item.graphName)),
                        contentDescription = ""
                    )
                })
        }
    }
}
private fun getIcoForScreen(screenName:String): Int {
    return when(screenName){
        "Countdown"-> R.drawable.ic_santa
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
}