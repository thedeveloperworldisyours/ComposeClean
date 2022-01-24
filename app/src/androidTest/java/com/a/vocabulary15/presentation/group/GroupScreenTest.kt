package com.a.vocabulary15.presentation.group

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.a.vocabulary15.util.TestTags
import com.a.vocabulary15.di.DatabaseModule
import com.a.vocabulary15.presentation.MainActivity
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import com.a.vocabulary15.presentation.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class GroupScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            Vocabulary15Theme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.GroupScreen.route
                ) {
                    composable(route = Screen.GroupScreen.route) {
                        GroupScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun testGroupListIfGroupsAreEmpty(){
        composeRule.onNodeWithTag(TestTags.GROUP_LIST)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun clickNewGroup_isDisplayed(){
        composeRule.onNodeWithTag(TestTags.NEW_GROUP_DIALOG).assertDoesNotExist()
        composeRule.onNodeWithContentDescription(TestTags.NEW_GROUP).performClick()
        composeRule.onNodeWithTag(TestTags.NEW_GROUP_DIALOG).assertIsDisplayed()
    }

}