package com.example.cat.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cat.models.Cat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatRepository {
    private val url = "https://anbo-restlostcats.azurewebsites.net/api/"
    private val catService: CatService
    val catLiveData: MutableLiveData<List<Cat>> = MutableLiveData<List<Cat>>()
    private var catLiveData2: MutableLiveData<List<Cat>> = MutableLiveData<List<Cat>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        //val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            //.addConverterFactory(KotlinJsonAdapterFactory)
            //.addConverterFactory(MoshiConverterFactory.create(moshi)) // Moshi, added to Gradle dependencies
            .build()
        catService = build.create(CatService::class.java)
        getPosts()
    }
    fun getPosts() {
        catService.getAllCats().enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                if (response.isSuccessful) {
                    //Log.d("APPLE", response.body().toString())
                    val b: List<Cat>? = response.body()
                    catLiveData.postValue(b!!)
                    catLiveData2.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
                //booksLiveData.postValue(null)
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
    fun add(cat: Cat) {
        catService.saveCat(cat).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
    fun delete(id: Int) {
        catService.deleteCat(id).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun update(cat: Cat) {
        catService.updateCat(cat.id, cat).enqueue(object : Callback<Cat> {
            override fun onResponse(call: Call<Cat>, response: Response<Cat>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Updated: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Cat>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }
    fun sortByReward(){
        catLiveData.value = catLiveData.value?.sortedBy { it.reward }
    }
    fun sortByRewardDescending(){
        catLiveData.value = catLiveData.value?.sortedByDescending { it.reward }
    }
    fun sortByName(){
        catLiveData.value = catLiveData.value?.sortedBy { it.name.lowercase() }
    }
    fun filterByName(name: String){
        if (name.isBlank()){
            getPosts()
        } else{
            catLiveData.value = catLiveData2.value
            catLiveData.value = catLiveData.value?.filter { cat -> cat.name.startsWith(name, ignoreCase = true) }
        }
    }

}