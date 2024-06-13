package com.projects.recyclerview_advanced.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.projects.recyclerview_advanced.data.CharacterDataSource
import com.projects.recyclerview_advanced.data.entities.Character
import kotlinx.coroutines.flow.Flow

class MyViewModel : ViewModel() {
    val pagedCharacters: Flow<PagingData<Character>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = { CharacterDataSource() }
    ).flow.cachedIn(viewModelScope)

}