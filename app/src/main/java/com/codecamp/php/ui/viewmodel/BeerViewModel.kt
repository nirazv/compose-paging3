package com.codecamp.php.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.codecamp.php.domain.usecase.BeerPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    beerPagedUseCase: BeerPagedUseCase
) : ViewModel() {
    val beerPagingFlow = beerPagedUseCase().cachedIn(viewModelScope)
}