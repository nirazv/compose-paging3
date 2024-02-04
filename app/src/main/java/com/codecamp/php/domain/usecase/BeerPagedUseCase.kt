package com.codecamp.php.domain.usecase

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.codecamp.php.data.local.BeerDatabase
import com.codecamp.php.data.mapper.toDomainModel
import com.codecamp.php.data.paging.BeerRemoteMediator
import com.codecamp.php.data.remote.BeerApi
import com.codecamp.php.domain.model.Beer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BeerPagedUseCase @Inject constructor(
    private val beerApi: BeerApi,
    private val beerDatabase: BeerDatabase
) {
    @OptIn(ExperimentalPagingApi::class)
    operator fun invoke(): Flow<PagingData<Beer>> {
        return Pager(
            config = PagingConfig(20),
            remoteMediator = BeerRemoteMediator(
                beerApi = beerApi,
                beerDatabase = beerDatabase
            ),
            pagingSourceFactory = { beerDatabase.beerDao.getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }
    }
}