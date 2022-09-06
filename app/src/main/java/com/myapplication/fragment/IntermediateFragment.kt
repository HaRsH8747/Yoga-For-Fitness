package com.myapplication.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myapplication.R
import com.myapplication.adapter.YogaAdapter
import com.myapplication.databinding.FragmentIntermediateBinding
import com.myapplication.models.YogaListX
import com.myapplication.utils.Utils


class IntermediateFragment : Fragment() {

    private lateinit var binding: FragmentIntermediateBinding
    private lateinit var adapter: YogaAdapter
    private var intermediateList = mutableListOf<YogaListX>()
    companion object{
        lateinit var intermediateFragment: IntermediateFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntermediateBinding.inflate(layoutInflater, container, false)
        intermediateFragment = this
        intermediateList.addAll(Utils.intermediateList)
        binding.rvIntermediate.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = YogaAdapter(intermediateList,requireContext())
        binding.rvIntermediate.adapter = adapter

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchList(){
//        binding.rvAll.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        val adapter = YogaAdapter(Utils.yogaSearchList,requireContext())
//        binding.rvAll.adapter = adapter
        intermediateList.clear()
        intermediateList.addAll(Utils.yogaSearchList)
        adapter.notifyDataSetChanged()
    }
}