package com.vismay.makeanote.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vismay.makeanote.ui.notes.HomeRoute
import com.vismay.makeanote.ui.notes.HomeViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.vismay.makeanote.ui.createupdatenote.CreateNoteRoute
import com.vismay.makeanote.ui.createupdatenote.CreateUpdateViewModel

@Composable
fun MakeANoteNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MakeANoteDestinations.HOME_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = MakeANoteDestinations.HOME_ROUTE
        ) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen
            )
        }
        composable(MakeANoteDestinations.CREATE_NOTE_ROUTE) {
            val createUpdateViewModel: CreateUpdateViewModel = hiltViewModel()
            CreateNoteRoute(
                createUpdateViewModel = createUpdateViewModel,
                isExpandedScreen = isExpandedScreen
            )
        }
    }
}