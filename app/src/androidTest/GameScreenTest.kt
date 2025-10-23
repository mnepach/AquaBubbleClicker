package com.example.aquabubbleclicker

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.aquabubbleclicker.presentation.ui.GameScreen
import com.example.aquabubbleclicker.ui.theme.AquaBubbleTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GameScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun gameScreen_displaysScore() {
        composeTestRule.setContent {
            AquaBubbleTheme {
                GameScreen()
            }
        }

        composeTestRule.onNodeWithText("Score: 0").assertIsDisplayed()
    }
}