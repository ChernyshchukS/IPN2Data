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
import androidx.navigation.toRoute

import pp.chsv.ipn2data.ui.screens.ValidateScreen
import pp.chsv.ipn2data.ui.screens.DecodeScreen
import pp.chsv.ipn2data.ui.screens.GenerateScreen
import pp.chsv.ipn2data.ui.screens.AboutScreen
import pp.chsv.ipn2data.ui.screens.ResultScreen

import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import pp.chsv.ipn2data.R

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val backStackEntry by
    navController.currentBackStackEntryAsState()

    val currentDestination = backStackEntry?.destination

    val mainDestinations = listOf(
        AppDestination.Validate,
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

                val title = when (destination) {
                    AppDestination.Validate -> "Проверка"
                    AppDestination.Decode -> "Расшифровка"
                    AppDestination.Generate -> "Генерация"
                    AppDestination.About -> "О программе"
                    else -> ""
                }

                // Проверяем, выбран ли текущий раздел
                val selected = currentMainDestination == destination

                // Выбираем иконку в зависимости от состояния
                val icon = when (destination) {

                    AppDestination.Validate ->
                        if (selected) R.drawable.ic_fact_check_filled
                        else R.drawable.ic_fact_check_outlined

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
                            contentDescription = title
                        )
                    },

                    label = {
                        Text(text = title)
                    },

                    selected = selected,

                    onClick = {
                        navController.navigate(destination) {

                            popUpTo(AppDestination.Validate) {
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
        startDestination = AppDestination.Validate,
        modifier = Modifier.fillMaxSize()
    ) {

        // Проверка
        composable<AppDestination.Validate> {

            ValidateScreen(

                onShowResult = { inputNumber ->

                    navController.navigate(
                        AppDestination.Result(inputNumber)
                    )

                }
            )
        }

        // Расшифровка
        composable<AppDestination.Decode> {

            DecodeScreen()
        }

        // Генерация
        composable<AppDestination.Generate> {

            GenerateScreen()
        }

        // О программе
        composable<AppDestination.About> {

            AboutScreen()
        }

        // Вложенный экран результата
        composable<AppDestination.Result> { entry ->

            val arguments =
                entry.toRoute<AppDestination.Result>()

            ResultScreen(

                inputNumber = arguments.inputNumber,

                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}