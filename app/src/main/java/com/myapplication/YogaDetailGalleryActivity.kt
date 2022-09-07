package com.myapplication

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
import com.myapplication.utils.Utils
import com.smarteist.autoimageslider.SliderView


class YogaDetailGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYogaDetailGalleryBinding
    private val sliderList = mutableListOf<String>()
    private lateinit var mediaPlayer: MediaPlayer
    private var toggleAudio: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaDetailGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                    toggleAudio = !toggleAudio
                    if (mediaPlayer.isPlaying){
                        mediaPlayer.stop()
                        mediaPlayer.reset()
                        mediaPlayer.release()
                    }
                }else{
                    Log.d("CLEAR","Play")
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

        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
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