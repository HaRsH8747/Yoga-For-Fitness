package com.myapplication

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.myapplication.api.RetrofitInstance
import com.myapplication.databinding.ActivityCategoryBinding
import com.myapplication.fragment.AllFragment
import com.myapplication.fragment.BeginnersFragment
import com.myapplication.fragment.IntermediateFragment
import com.myapplication.fragment.ViewPagerAdapter
import com.myapplication.models.Category
import com.myapplication.models.YogaListX
import com.myapplication.utils.Utils
import com.myapplication.utils.Utils.Companion.categoryResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    var isFirstTime: Boolean = false
    private lateinit var snackbar: Snackbar
    var tabTitle = arrayOf("ALL","BEGINNERS","INTERMEDIATE","EXPERT","MY FAVOURITES")
    val token: String = "xaZPmP8Fu9hdbhHasB"
    private lateinit var result: Call<Category>
    var toggleSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_YogaForFitness)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        StartActivity.startActivity.finish()
//        binding = ActivityCategoryBinding.inflate(layoutInflater)
//        val tv = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("BEGINNERS").setCustomView(tv),1)
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("INTERMEDIATE").setCustomView(tv),2)
//        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val removePos = categoryResponse.type.size + 1
        for (item in 1 until categoryResponse.type.size+1){
            binding.tabLayout.getTabAt(item)?.text = categoryResponse.type[item-1].title.uppercase()
        }
        for (pos in removePos until binding.tabLayout.tabCount-1){
            binding.tabLayout.removeTabAt(removePos)
        }

        binding.viewpager.adapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
//        binding.viewpager.offscreenPageLimit = categoryResponse.type.size
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewpager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("ALL").setCustomView(tv),0, true)
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("BEGINNERS").setCustomView(tv),1)
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("INTERMEDIATE").setCustomView(tv),2)
//        var pos = 1
//        for (type in categoryResponse.type){
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(type.title.uppercase()).setCustomView(tv),pos)
//            pos++
//        }

//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("MY FAVOURITES").setCustomView(tv),categoryResponse.type.size+1)

        binding.viewpager.currentItem = 0

//        fetchCategoryData()

        binding.ivSearch.setOnClickListener {
            if(toggleSearch){
                toggleSearch = !toggleSearch
                binding.tvE.visibility = View.VISIBLE
                binding.etSearch.visibility = View.INVISIBLE
                binding.ivSearch.setImageResource(R.drawable.ic_baseline_search_24)
//                hideSoftKeyboard(this)
            }else{
                toggleSearch = !toggleSearch
                binding.tvE.visibility = View.INVISIBLE
                binding.etSearch.visibility = View.VISIBLE
                binding.etSearch.requestFocus()
                binding.ivSearch.setImageResource(R.drawable.ic_baseline_close_24)
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editText: Editable?) {
                binding.etSearch.removeTextChangedListener(this)
                Utils.yogaSearchList.clear()
                Utils.yogaSearchList.addAll(getSearchList(binding.etSearch.text.toString()))
//                when(binding.viewpager2.currentItem){
//                    0 -> AllFragment.allFragment.updateSearchList()
//                    1 -> BeginnersFragment.beginnersFragment.updateSearchList()
//                    2 -> IntermediateFragment.intermediateFragment.updateSearchList()
//                }
                binding.etSearch.addTextChangedListener(this)
            }
        })

//        binding.etSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                Utils.yogaSearchList.clear()
//                Utils.yogaSearchList.addAll(getSearchList(binding.etSearch.text.toString()))
//                when(binding.viewpager2.currentItem){
//                    0 -> AllFragment.allFragment.updateSearchList()
//                    1 -> BeginnersFragment.beginnersFragment.updateSearchList()
//                    2 -> IntermediateFragment.intermediateFragment.updateSearchList()
//                }
//            }
//            false
//        }

//        for (i in 0 until binding.tabLayout.tabCount) {
//            val tv = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
//            binding.tabLayout.getTabAt(i)?.customView = tv
//        }

//        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->}.attach()
    }

//    fun hideSoftKeyboard(activity: Activity) {
//        val inputMethodManager = activity.getSystemService(
//            INPUT_METHOD_SERVICE) as InputMethodManager
//        if (inputMethodManager.isAcceptingText) {
//            inputMethodManager.hideSoftInputFromWindow(
//                activity.currentFocus!!.windowToken,
//                0
//            )
//        }
//    }

    private fun getSearchList(searchText: String): List<YogaListX> {
        return if (searchText.isNotEmpty()){
            Utils.yogaList.filter { it.title.lowercase().contains(searchText.lowercase()) }
//            when(binding.viewpager2.currentItem){
//                0 ->
//                1 -> Utils.beginnersList.filter { it.title.lowercase().contains(searchText.lowercase()) }
//                2 -> Utils.intermediateList.filter { it.title.lowercase().contains(searchText.lowercase()) }
//                else -> Utils.allYogaList.filter { it.title.lowercase().contains(searchText.lowercase()) }
//            }
        }else{
            Utils.yogaList
//            when(binding.viewpager2.currentItem){
//                0 ->
//                1 -> Utils.beginnersList
//                2 -> Utils.intermediateList
//                else -> Utils.allYogaList
//            }
        }
    }

//    private fun fetchCategoryData() {
//        val yogaApi = RetrofitInstance.api
//        GlobalScope.launch {
//            result = yogaApi.getCategories(token)
//            result.enqueue(object : Callback<Category> {
//                override fun onResponse(call: Call<Category>, response: Response<Category>) {
//                    if(response.body() != null){
//                        categoryResponse = response.body()!!
////                        startActivity(intent)
////                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
////                        finish()
//                        Log.d("CLEAR","response: ${categoryResponse}")
//                    }
//                }
//                override fun onFailure(call: Call<Category>, t: Throwable) {
//                    Log.d("CLEAR","MainActivity: ${t.message}")
//                    Toast.makeText(this@CategoryActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//    }
}