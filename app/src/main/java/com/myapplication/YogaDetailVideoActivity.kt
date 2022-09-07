package com.myapplication

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapplication.adapter.YogaBenefitsAdapter
import com.myapplication.databinding.ActivityYogaDetailVideoBinding
import com.myapplication.utils.Utils

class YogaDetailVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYogaDetailVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaDetailVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
}