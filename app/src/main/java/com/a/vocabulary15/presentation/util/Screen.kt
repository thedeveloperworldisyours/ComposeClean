package com.a.vocabulary15.presentation.util

sealed class Screen(val route: String) {
    object GroupScreen: Screen("group_screen")
    object ElementScreen: Screen("element_screen")
    object TestScreen: Screen("test_screen")
    object StatisticsScreen: Screen("statistics_screen")
}
