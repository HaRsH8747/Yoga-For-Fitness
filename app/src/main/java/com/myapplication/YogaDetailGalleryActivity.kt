package com.myapplication

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapplication.adapter.SliderAdapter
import com.myapplication.adapter.YogaBenefitsAdapter
import com.myapplication.databinding.ActivityYogaDetailGalleryBinding
import com.myapplication.utils.AppPref
import com.myapplication.utils.Utils
import com.smarteist.autoimageslider.SliderView
import java.util.regex.Pattern


class YogaDetailGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYogaDetailGalleryBinding
    private val sliderList = mutableListOf<String>()
    private lateinit var mediaPlayer: MediaPlayer
    private var toggleAudio: Boolean = false
    private lateinit var appPref: AppPref
    private var isFavourite = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaDetailGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appPref = AppPref(this)
        mediaPlayer = MediaPlayer()
        sliderList.addAll(Utils.yogaDetailGallery.details.media.gallery.media)
        val adapter = SliderAdapter(this, sliderList)
        binding.imageSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        binding.imageSlider.setSliderAdapter(adapter)
        binding.imageSlider.scrollTimeInSec = 3
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.startAutoCycle()

        binding.tvYogaName.text = Utils.yogaDetailGallery.details.title
        binding.tvYogaDetail.text = Utils.yogaDetailGallery.details.description

        binding.rvBenefits.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val benefitAdapter = YogaBenefitsAdapter(this,Utils.yogaDetailGallery.details.benefit)
        binding.rvBenefits.adapter = benefitAdapter

        if (Utils.yogaDetailGallery.details.media.gallery.audio != "false"){
            binding.btnAudio.visibility = View.VISIBLE
        }
        binding.btnAudio.setOnClickListener {
            try {
                if (toggleAudio){
                    binding.btnAudio.setBackgroundResource(R.drawable.ic_music)
                    toggleAudio = !toggleAudio
                    if (mediaPlayer.isPlaying){
                        mediaPlayer.stop()
                        mediaPlayer.reset()
                        mediaPlayer.release()
                    }
                }else{
                    binding.btnAudio.setBackgroundResource(R.drawable.ic_pause)
                    toggleAudio = !toggleAudio
                    mediaPlayer = MediaPlayer()
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mediaPlayer.setDataSource(Utils.yogaDetailGallery.details.media.gallery.audio)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

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

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            if (mediaPlayer.isPlaying){
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}