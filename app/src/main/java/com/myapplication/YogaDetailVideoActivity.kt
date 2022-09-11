package com.myapplication

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapplication.adapter.YogaBenefitsAdapter
import com.myapplication.databinding.ActivityYogaDetailVideoBinding
import com.myapplication.utils.AppPref
import com.myapplication.utils.Utils
import java.util.regex.Pattern

class YogaDetailVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYogaDetailVideoBinding
    private lateinit var appPref: AppPref
    private var isFavourite = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaDetailVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appPref = AppPref(this)
        val uri: Uri = Uri.parse(Utils.yogaDetailVideo.details.media)
        binding.videoView.setVideoURI(uri)
        binding.videoView.start()
        binding.videoView.setOnClickListener {
            if (binding.videoView.isPlaying){
                binding.videoView.pause()
            }else{
                binding.videoView.start()
            }
        }

        binding.tvYogaName.text = Utils.yogaDetailVideo.details.title
        binding.tvYodaDetail.text = Utils.yogaDetailVideo.details.description

        binding.rvBenefits.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val benefitAdapter = YogaBenefitsAdapter(this,Utils.yogaDetailVideo.details.benefit)
        binding.rvBenefits.adapter = benefitAdapter

        var favourites = appPref.getString(AppPref.FAVOURITE_YOGA)
        var newFavourites = ""
        if (!favourites.isNullOrEmpty()){
            isFavourite = checkForFavourite(favourites)
        }
        if (isFavourite){
            binding.btnFav.text = "Remove From Favourites"
        }else{
            binding.btnFav.text = "Add to Favourites"
        }
        binding.btnFav.setOnClickListener {
            if (isFavourite){
                isFavourite = !isFavourite
                val pattern = Pattern.compile(",")
                for (item in pattern.split(favourites!!)){
                    if (item != Utils.currentYogaId.toString()){
                        newFavourites += "$item,"
                    }
                }
                favourites = newFavourites
                appPref.setString(AppPref.FAVOURITE_YOGA,newFavourites)
                binding.btnFav.text = "Add to Favourites"
            }else{
                isFavourite = !isFavourite
                favourites += "${Utils.currentYogaId},"
                appPref.setString(AppPref.FAVOURITE_YOGA,favourites)
                binding.btnFav.text = "Remove from Favourites"
            }
        }

        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun checkForFavourite(favouriteString: String?): Boolean {
        val pattern = Pattern.compile(",")
        val favourites = pattern.split(favouriteString!!)
        for (item in favourites){
            if (item == (Utils.currentYogaId).toString()){
                return true
            }
        }
        return false
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
}