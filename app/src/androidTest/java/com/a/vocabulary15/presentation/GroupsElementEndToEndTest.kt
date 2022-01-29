package com.a.vocabulary15.presentation

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.test.platform.app.InstrumentationRegistry
import com.a.vocabulary15.di.DatabaseModule
import com.a.vocabulary15.presentation.element.composables.ElementScreen
import com.a.vocabulary15.presentation.group.GroupScreen
import com.a.vocabulary15.presentation.test.composables.TestScreen
import com.a.vocabulary15.presentation.ui.theme.Vocabulary15Theme
import com.a.vocabulary15.presentation.util.Screen
import com.a.vocabulary15.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class GroupsElementEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    lateinit var instrumentationContext: Context

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        hiltRule.inject()
        composeRule.setContent {
            Vocabulary15Theme {
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
                        ElementScreen(instrumentationContext, navController)
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

    @Test
    fun emptyGroupList() {
        composeRule.onNodeWithTag(TestTags.EMPTY_GROUP_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun saveNewGroup() {
        composeRule.onNodeWithTag(TestTags.NEW_GROUP_DIALOG).assertDoesNotExist()

        composeRule.onNodeWithContentDescription(TestTags.NEW_GROUP).performClick()

        composeRule.onNodeWithTag(TestTags.NEW_GROUP_DIALOG).assertIsDisplayed()
        composeRule
            .onNodeWithTag(TestTags.NAME_TEXT_FIELD)
            .performTextInput("new-name-group")
        composeRule.onNodeWithTag(TestTags.SAVE_GROUP).performClick()

        composeRule.onNodeWithText("new-name-group").assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.GROUP_LIST)
            .onChildren()
            .onFirst()
            .assert(hasText("new-name-group"))
    }

    @Test
    fun emptyElementList() {
        saveNewGroup()
        composeRule.onNodeWithTag(TestTags.EMPTY_ELEMENT_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun saveElements(){
        saveNewGroup()
        composeRule.onNodeWithTag(TestTags.GROUP_LIST)
            .onChildren()
            .onFirst()
            .performClick()

        composeRule.onNodeWithTag(TestTags.NEW_ELEMENT).performClick()

        composeRule.onNodeWithTag(TestTags.NEW_NAME_ELEMENT_TEXT_FIELD)
            .performTextInput("new-name-element")

        composeRule.onNodeWithTag(TestTags.NEW_LINK_ELEMENT_TEXT_FIELD)
            .performTextInput("new-link-element")

        composeRule.onNodeWithTag(TestTags.NEW_IMAGE_ELEMENT_TEXT_FIELD)
            .performTextInput("new-image-element")

        composeRule.onNodeWithTag(TestTags.SAVE_ELEMENT)

        composeRule.onNodeWithTag(TestTags.ELEMENT_LIST).assertIsDisplayed()

    }

    @Test
    fun emptyTest(){
        saveNewGroup()

        composeRule.onNodeWithTag(TestTags.CHECK_YOUR_KNOWLEDGE)

        composeRule.onNodeWithTag(TestTags.EMPTY_ELEMENT_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun chooseListLevel() {
        saveElements()

        composeRule.onNodeWithTag(TestTags.CHECK_YOUR_KNOWLEDGE)

        composeRule.onNodeWithTag(TestTags.CHOOSE_LEVEL_DIALOG).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.LIST_LEVEL_BUTTON).performClick()

        composeRule.onNodeWithTag(TestTags.ELEMENT_LIST_OPTIONS).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.SCORE).assertIsDisplayed()
    }

    @Test
    fun chooseWordLevel() {
        saveElements()

        composeRule.onNodeWithTag(TestTags.CHECK_YOUR_KNOWLEDGE)

        composeRule.onNodeWithTag(TestTags.CHOOSE_LEVEL_DIALOG).assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.ENTER_NAME_ELEMENT_TEXT_FIELD).performClick()

        composeRule.onNodeWithTag(TestTags.SCORE).assertIsDisplayed()
    }
}