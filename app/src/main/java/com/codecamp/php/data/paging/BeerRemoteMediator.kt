package com.codecamp.php.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.codecamp.php.data.local.BeerDatabase
import com.codecamp.php.data.local.BeerEntity
import com.codecamp.php.data.mapper.toEntityModel
import com.codecamp.php.data.remote.BeerApi
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerApi: BeerApi,
    private val beerDatabase: BeerDatabase
): RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) return MediatorResult.Success(endOfPaginationReached = true)
                    (lastItem.id / state.config.pageSize) + 1
                }
            }

            val beers = beerApi.getBeer(
                page = loadKey,
                pageSize = 20
            )

            beerDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    beerDatabase.beerDao.clearAll()
                }

                val beerEntities = beers.map { it.toEntityModel() }
                beerDatabase.beerDao.addAll(beerEntities)
            }

            MediatorResult.Success(endOfPaginationReached = beers.isEmpty())

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}