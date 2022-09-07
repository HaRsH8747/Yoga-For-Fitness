package com.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapplication.R
import com.myapplication.databinding.YogaBenefitItemBinding
import com.myapplication.models.yogabenefit.Benefit

class YogaBenefitsAdapter(
    val context: Context,
    val benefitsList: List<Benefit>
): RecyclerView.Adapter<YogaBenefitsAdapter.YogaBenefitViewHolder>() {

    inner class YogaBenefitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var yogaBenefitItemBinding: YogaBenefitItemBinding

        init {
            yogaBenefitItemBinding = YogaBenefitItemBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YogaBenefitViewHolder {
        return YogaBenefitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.yoga_benefit_item,parent,false))
    }

    override fun onBindViewHolder(holder: YogaBenefitViewHolder, position: Int) {
        val benefit = benefitsList[position]
        Glide.with(context).load(benefit.benefit_icon).into(holder.yogaBenefitItemBinding.ivBenefitIcon)
        holder.yogaBenefitItemBinding.tvBenefitText.text = benefit.benefit_text
    }

    override fun getItemCount(): Int {
        return benefitsList.size
    }
}