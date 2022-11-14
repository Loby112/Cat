package com.example.cat.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cat.repository.AuthRepository
import com.example.cat.repository.CatRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel: ViewModel() {
    private val repository = AuthRepository()
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val userLiveData = repository.userLiveData

    fun login(email: String, password: String){
        repository.login(email, password)
    }
    fun signout(){
        repository.signout()
    }

    fun createUser(email: String, password: String){
        repository.createUser(email, password)
    }

}