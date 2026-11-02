package pp.chsv.ipn2data.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import pp.chsv.ipn2data.ui.screens.DecodeScreen
import pp.chsv.ipn2data.ui.screens.GenerateScreen
import pp.chsv.ipn2data.ui.screens.AboutScreen

import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import pp.chsv.ipn2data.R
import pp.chsv.rnokpp.Rnokpp

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val backStackEntry by
    navController.currentBackStackEntryAsState()

    val currentDestination = backStackEntry?.destination

    val mainDestinations = listOf(
        AppDestination.Decode,
        AppDestination.Generate,
        AppDestination.About
    )

    val currentMainDestination =
        mainDestinations.find { destination ->

            currentDestination?.hasRoute(
                destination::class
            ) == true
        }

    val showNavigationBar =
        currentMainDestination != null

    val layoutType =
        if (showNavigationBar) {

            NavigationSuiteScaffoldDefaults
                .calculateFromAdaptiveInfo(
                    androidx.compose.material3.adaptive.currentWindowAdaptiveInfo()
                )

        } else {

            NavigationSuiteType.None

        }

    NavigationSuiteScaffold(

        layoutType = layoutType,

        navigationSuiteItems = {

            mainDestinations.forEach { destination ->

                val titleRes = when (destination) {
                    AppDestination.Decode -> R.string.decode_title
                    AppDestination.Generate -> R.string.generate_title
                    AppDestination.About -> R.string.about_title
                    else -> R.string.app_name
                }

                // Проверяем, выбран ли текущий раздел
                val selected = currentMainDestination == destination

                // Выбираем иконку в зависимости от состояния
                val icon = when (destination) {

                    AppDestination.Decode ->
                        if (selected) R.drawable.ic_manage_search_filled
                        else R.drawable.ic_manage_search_outlined

                    AppDestination.Generate ->
                        if (selected) R.drawable.ic_add_circle_filled
                        else R.drawable.ic_add_circle_outlined

                    AppDestination.About ->
                        if (selected) R.drawable.ic_info_filled
                        else R.drawable.ic_info_outlined

                    else -> R.drawable.ic_info_outlined
                }

                item(
                    icon = {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = stringResource(id = titleRes)
                        )
                    },

                    label = {
                        Text(text = stringResource(id = titleRes))
                    },

                    selected = selected,

                    onClick = {
                        navController.navigate(destination) {

                            popUpTo(AppDestination.Decode) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {

        AppNavHost(
            navController = navController
        )

    }
}


@Composable
private fun AppNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = AppDestination.Decode,
        modifier = Modifier.fillMaxSize()
    ) {

        // Декодирование
        composable<AppDestination.Decode> {

            DecodeScreen()
        }

        // Генерация
        composable<AppDestination.Generate> {

            GenerateScreen(
                generateRnokpp = { date, sequence, gender ->

                    Rnokpp.generate(
                        date,
                        sequence,
                        gender
                    )
                }
            )
        }

        // О программе
        composable<AppDestination.About> {

            AboutScreen()
        }
    }
}