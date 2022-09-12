package com.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapplication.R
import com.myapplication.YogaDetailGalleryActivity
import com.myapplication.YogaDetailVideoActivity
import com.myapplication.api.RetrofitInstance
import com.myapplication.databinding.YogaItemBinding
import com.myapplication.models.YogaListId
import com.myapplication.models.YogaListX
import com.myapplication.models.yogadetailgallery.YogaDetailGallery
import com.myapplication.models.yogadetailvideo.YogaDetailVideo
import com.myapplication.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllYogaAdapter(
    private val yogaList: MutableList<YogaListX>,
    val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var YOGA_ITEM = 0
//    private var AD_TYPE = 1
    private lateinit var resultGallery: Call<YogaDetailGallery>
    private lateinit var resultVideo: Call<YogaDetailVideo>

    inner class YogaItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var yogaItemBinding: YogaItemBinding
        init {
            yogaItemBinding = YogaItemBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.yoga_item, parent, false)
        return YogaItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val yogaItem = yogaList[position]
        Glide.with(context).load(yogaItem.yoga_image).into((holder as YogaItemViewHolder).yogaItemBinding.ivYoga)
        holder.yogaItemBinding.tvYoga.text = yogaItem.title
        holder.yogaItemBinding.cvItem.setOnClickListener {
            fetchYogaDetail(position)
        }
    }

    private fun fetchYogaDetail(position: Int) {
        val yogaApi = RetrofitInstance.api
        GlobalScope.launch {
            resultGallery = yogaApi.getYogaDetailGallery(yogaList[position].id.toString(),Utils.token)
            resultGallery.enqueue(object : Callback<YogaDetailGallery> {
                override fun onResponse(call: Call<YogaDetailGallery>, response: Response<YogaDetailGallery>) {
                    if(response.body() != null){
                        Utils.yogaDetailGallery = response.body()!!
                        Utils.currentYogaId = yogaList[position].id
                        val intent = Intent(context, YogaDetailGalleryActivity::class.java)
                        context.startActivity(intent)
                    }
                }
                override fun onFailure(call: Call<YogaDetailGallery>, t: Throwable) {
                    Log.d("CLEAR","MainActivity: ${t.message}")
                    fetchYogaDetailVideo(position)
//                        Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun fetchYogaDetailVideo(position: Int) {
        val yogaApi = RetrofitInstance.api
        GlobalScope.launch {
            resultVideo = yogaApi.getYogaDetailVideo(yogaList[position].id.toString(),Utils.token)
            resultVideo.enqueue(object : Callback<YogaDetailVideo> {
                override fun onResponse(call: Call<YogaDetailVideo>, response: Response<YogaDetailVideo>) {
                    if(response.body() != null){
                        Utils.yogaDetailVideo = response.body()!!
                        Utils.currentYogaId = yogaList[position].id
                        val intent = Intent(context, YogaDetailVideoActivity::class.java)
                        context.startActivity(intent)
                    }
                }
                override fun onFailure(call: Call<YogaDetailVideo>, t: Throwable) {
                    Log.d("CLEAR","MainActivity: ${t.message}")
//                        Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return yogaList.size
    }
}