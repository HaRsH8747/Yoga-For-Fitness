package com.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.myapplication.api.RetrofitInstance
import com.myapplication.models.Category
import com.myapplication.models.YogaList
import com.myapplication.models.YogaListId
import com.myapplication.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : AppCompatActivity() {

    val token: String = "xaZPmP8Fu9hdbhHasB"
    private lateinit var resultCategory: Call<Category>
    private lateinit var resultYogaList: Call<YogaList>
    private var isFetched = MutableLiveData(false)
//    companion object{
//        lateinit var startActivity: StartActivity
//    }
//    const val

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
//        startActivity = this
        fetchCategoryData()
        val intent = Intent(this, CategoryActivity::class.java)
        isFetched.observe(this){
            if (it){
                startActivity(intent)
                finish()
            }
        }
    }

    private fun fetchCategoryData() {
//        val intent = Intent(this, CategoryActivity::class.java)
        val yogaApi = RetrofitInstance.api
        GlobalScope.launch {
            resultCategory = yogaApi.getCategories(token)
            resultCategory.enqueue(object : Callback<Category> {
                override fun onResponse(call: Call<Category>, response: Response<Category>) {
                    if(response.body() != null){
                        Utils.categoryResponse = response.body()!!
                        fetchYogaList()
//                        startActivity(intent)
//                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
//                        finish()
                        Log.d("CLEAR","response: ${Utils.categoryResponse}")
                    }
                }
                override fun onFailure(call: Call<Category>, t: Throwable) {
                    Log.d("CLEAR","MainActivity: ${t.message}")
//                    Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun fetchYogaList(){
        val yogaApi = RetrofitInstance.api
        for (type in Utils.categoryResponse.type){
            GlobalScope.launch {
                resultYogaList = yogaApi.getYogaList(type.id.toString(),token)
                resultYogaList.enqueue(object : Callback<YogaList> {
                    override fun onResponse(call: Call<YogaList>, response: Response<YogaList>) {
                        if(response.body() != null){
                            Utils.allYogaList.add(YogaListId(type.id,response.body()!!.yogalist.toMutableList()))
//                            if (type.title == Utils.BEGINNERS){
//                            }else if (type.title == Utils.INTERMEDIATE){
//                                Utils.intermediateList = response.body()!!.yogalist.toMutableList()
//                            }
//                            Log.d("CLEAR","response:")
                            if (!isFetched.value!!){
                                isFetched.postValue(true)
                            }
    //                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
//                            finish()
                        }
                    }
                    override fun onFailure(call: Call<YogaList>, t: Throwable) {
                        Log.d("CLEAR","MainActivity: ${t.message}")
                        isFetched.postValue(false)
//                        Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}