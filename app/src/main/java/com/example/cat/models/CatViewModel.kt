package com.example.cat.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cat.repository.CatRepository

class CatViewModel: ViewModel() {
    private val repository = CatRepository()
    val catLiveData: LiveData<List<Cat>> = repository.catLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData
    var catList: LiveData<List<Cat>> = repository.catLiveData
    init {
        reload()
    }

    fun reload(){
        repository.getPosts()
    }

    operator fun get(index: Int): Cat? {
        return catLiveData.value?.get(index)
    }

    fun add(cat: Cat){
        repository.add(cat)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun update(cat: Cat) {
        repository.update(cat)
    }
    fun sortByReward(){
        repository.sortByReward()
    }
    fun sortByName(){
        repository.sortByName()
    }
    fun sortByRewardDescending(){
        repository.sortByRewardDescending()
    }
    fun filterByName(name: String){
        repository.filterByName(name)
    }
}