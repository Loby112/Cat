package com.example.cat.repository

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
                userLiveData.postValue(user)
            }
            else {
                /* val message = response.code().toString() + " " + response.message()
                errorMessageLiveData.postValue(message)
                Log.d("APPLE", message)'

                 */
            }
        }
    }
}
