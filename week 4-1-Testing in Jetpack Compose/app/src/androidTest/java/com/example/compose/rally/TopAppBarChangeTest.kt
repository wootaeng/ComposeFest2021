package com.example.compose.rally

import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick

import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test


/**액션(Action)을 사용하여 RallyTopAppBar의 다른 탭을 클릭하면 선택 항목이 변경되는지 확인한다. Action에 대한 부분은 Testing Cheat Sheet를 참조하자.

힌트:

    테스트 범위에는 RallyApp이 소유한 상태(State)가 포함되어야 한다.
    행동(behavior)이 아니라 상태(state)를 확인(verify)하자. 호출된 객체와 방법에 의존하는 대신 UI 상태에 대한 주장(assertion)을 사용한다.
*/
class TopAppBarChangeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun topAppBar_change() {
        val allScreens = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreens,
                onTabSelected = { },
                currentScreen = RallyScreen.Accounts
            )
        }

        composeTestRule
            .onNode(
                hasContentDescription(RallyScreen.Bills.name)
            )
            .performClick()
            .assertContentDescriptionContains(RallyScreen.Bills.name)
    }

}