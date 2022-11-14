package com.example.cat.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository {
    private var auth = Firebase.auth
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val user = auth.currentUser


    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener{task ->
            if(task.isSuccessful()){
                userLiveData.value = user
            }
            else {
                errorMessageLiveData.value = task.exception?.message.toString()
                Log.w(TAG, "signInWithEmail:failure", task.exception)
            }
        }
    }

    fun signout(){
        auth.signOut()
        userLiveData.value = auth.currentUser
    }

    fun createUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userLiveData.value = user
                // Alternative: goto next fragment (no need to login after register)
            } else {
                errorMessageLiveData.value = task.exception?.message.toString()
            }
        }
    }



}
