package com.myapplication.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myapplication.adapter.YogaAdapter
import com.myapplication.databinding.FragmentMyFavouritesBinding
import com.myapplication.utils.AppPref
import com.myapplication.utils.Utils
import java.util.regex.Pattern

class MyFavouritesFragment : Fragment() {

    private lateinit var binding: FragmentMyFavouritesBinding
    private lateinit var appPref: AppPref
    private lateinit var adapter: YogaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyFavouritesBinding.inflate(layoutInflater, container, false)
        appPref = AppPref(requireContext())

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (!appPref.getString(AppPref.FAVOURITE_YOGA).isNullOrEmpty()){
            binding.rvFavourite.visibility = View.VISIBLE
            binding.tvFavourite.visibility = View.INVISIBLE
            getFavourites()
            binding.rvFavourite.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = YogaAdapter(Utils.favouritesList, requireContext())
            binding.rvFavourite.adapter = adapter
        }else{
            binding.rvFavourite.visibility = View.INVISIBLE
            binding.tvFavourite.visibility = View.VISIBLE
        }
    }

    private fun getFavourites() {
        val pattern = Pattern.compile(",")
        val favouriteString: String = appPref.getString(AppPref.FAVOURITE_YOGA)!!
        val favourites = pattern.split(favouriteString)
        Utils.favouritesList.clear()
        for (item in Utils.beginnersList){
            for (fav in favourites){
                if (item.id == fav.toInt()){
                    Utils.favouritesList.add(item)
                }
            }
        }

        for (item in Utils.intermediateList){
            for (fav in favourites){
                if (item.id == fav.toInt()){
                    Utils.favouritesList.add(item)
                }
            }
        }
    }

//    private fun fetchYogaDetail(yogaId: String) {
//        val yogaApi = RetrofitInstance.api
//        GlobalScope.launch {
//            resultGallery = yogaApi.getYogaDetailGallery(yogaId,Utils.token)
//            resultGallery.enqueue(object : Callback<YogaDetailGallery> {
//                override fun onResponse(call: Call<YogaDetailGallery>, response: Response<YogaDetailGallery>) {
//                    if(response.body() != null){
//                        Utils.favouriteListGallery.add(response.body()!!)
//                    }
//                }
//                override fun onFailure(call: Call<YogaDetailGallery>, t: Throwable) {
//                    Log.d("CLEAR","MainActivity: ${t.message}")
//                    fetchYogaDetailVideo(yogaId)
////                        Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//    }
//
//    private fun fetchYogaDetailVideo(yogaId: String) {
//        val yogaApi = RetrofitInstance.api
//        GlobalScope.launch {
//            resultVideo = yogaApi.getYogaDetailVideo(yogaId,Utils.token)
//            resultVideo.enqueue(object : Callback<YogaDetailVideo> {
//                override fun onResponse(call: Call<YogaDetailVideo>, response: Response<YogaDetailVideo>) {
//                    if(response.body() != null){
//                        Utils.favouriteListVideo.add(response.body()!!)
//                    }
//                }
//                override fun onFailure(call: Call<YogaDetailVideo>, t: Throwable) {
//                    Log.d("CLEAR","MainActivity: ${t.message}")
////                        Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//    }
}