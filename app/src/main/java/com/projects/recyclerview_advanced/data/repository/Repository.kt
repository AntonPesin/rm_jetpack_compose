package com.projects.recyclerview_advanced.data.repository

import com.projects.recyclerview_advanced.data.entities.Character
import com.projects.recyclerview_advanced.data.remote.RetrofitAPI

class Repository {

    suspend fun getCharacters(page: Int): List<Character> {
        return RetrofitAPI.search.getCharacters(page).results
    }
}