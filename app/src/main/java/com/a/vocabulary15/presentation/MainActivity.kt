package com.a.vocabulary15.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.a.vocabulary15.presentation.element.ElementScreen
import com.a.vocabulary15.presentation.group.GroupScreen
import com.a.vocabulary15.presentation.test.composables.TestScreen
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import com.a.vocabulary15.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vocabulary15Theme {
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.GroupScreen.route
                    ) {
                        composable(route = Screen.GroupScreen.route) {
                            GroupScreen(navController)
                        }
                        composable(route = Screen.ElementScreen.route +
                                "?idGroup={idGroup}&elementName={elementName}",
                        arguments = listOf(
                            navArgument(name = "idGroup") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "elementName") {
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )) {
                            ElementScreen(this@MainActivity, navController)
                        }
                        composable(route = Screen.TestScreen.route +
                                "?idGroup={idGroup}&elementName={elementName}",
                            arguments = listOf(
                                navArgument(name = "idGroup") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "elementName") {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )) {
                            TestScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

