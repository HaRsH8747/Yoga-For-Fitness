package com.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapplication.R
import com.myapplication.databinding.YogaItemBinding
import com.myapplication.models.YogaListX

class YogaAdapter(
    private val yogaList: MutableList<YogaListX>,
    val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var YOGA_ITEM = 0
//    private var AD_TYPE = 1

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
    }

    override fun getItemCount(): Int {
        return yogaList.size
    }
}