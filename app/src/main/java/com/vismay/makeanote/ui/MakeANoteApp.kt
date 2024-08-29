package com.vismay.makeanote.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.vismay.makeanote.ui.ui.theme.MakeANoteTheme

@Composable
fun MakeANoteApp(widthSizeClass: WindowWidthSizeClass) {
    MakeANoteTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            MakeANoteNavigationActions(navController)
        }
        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded

        MakeANoteNavGraph(
            isExpandedScreen = isExpandedScreen,
            navController = navController
        )

    }
}