package com.projects.recyclerview_advanced.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.projects.recyclerview_advanced.data.entities.Character
import com.projects.recyclerview_advanced.data.repository.Repository

class CharacterDataSource : PagingSource<Int, Character>() {
    private val repository = Repository()
    override fun getRefreshKey(state: PagingState<Int, Character>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return kotlin.runCatching {
            repository.getCharacters(page)
        }.fold(
            onSuccess = {
                if (it.isEmpty()) {
                    LoadResult.Error(Exception("Empty result"))
                } else {
                    LoadResult.Page(
                        data = it,
                        prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                        nextKey = if (it.isEmpty()) null else page + 1
                    )
                }
            }, onFailure = {
                LoadResult.Error(it) }
        )
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}



