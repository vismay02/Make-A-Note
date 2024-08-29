package com.vismay.makeanote.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vismay.makeanote.R
import com.vismay.makeanote.ui.components.CustomSearchBar
import com.vismay.makeanote.ui.ui.theme.MakeANoteTheme

@Composable
fun NotesScreen(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        HomeScreenWithNotes(modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithNotes(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarColors(
                    containerColor = colorResource(id = R.color.purple_700),
                    scrolledContainerColor = colorResource(id = R.color.cardview_dark_background),
                    navigationIconContentColor = colorResource(id = R.color.cardview_dark_background),
                    titleContentColor = colorResource(id = R.color.white),
                    actionIconContentColor = colorResource(id = R.color.cardview_dark_background),
                ),
                modifier = modifier
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(colorResource(id = R.color.black)),
        ) {
            Column {
                CustomSearchBar("")
                Spacer(modifier = modifier.height(14.dp))
                LazyColumn {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MakeANoteTheme {
        HomeScreenWithNotes()
    }
}