package com.codecamp.php.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.codecamp.php.domain.model.Beer
import com.codecamp.php.ui.viewmodel.BeerViewModel

@Composable
fun BeerScreen() {
    val viewModel: BeerViewModel = hiltViewModel()
    val beerPagingFlow: LazyPagingItems<Beer> = viewModel.beerPagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = beerPagingFlow.loadState) {
        if (beerPagingFlow.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (beerPagingFlow.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = { BeerTopAppBar() },
        content = { paddingValues -> BeerContent(paddingValues, beerPagingFlow) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeerTopAppBar() {
    TopAppBar(title = { Text(text = "BeerTopAppBar") })
}

@Composable
fun BeerContent(paddingValues: PaddingValues, beerPagingFlow: LazyPagingItems<Beer>) {
    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        if (beerPagingFlow.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp), content = {
                items(count = beerPagingFlow.itemCount) {
                    BeerItem(beerPagingFlow[it]!!)
                }
                item {
                    if (beerPagingFlow.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            })
        }
    }

}
