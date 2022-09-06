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
import com.myapplication.databinding.FragmentAllBinding
import com.myapplication.models.YogaListX
import com.myapplication.utils.Utils

class AllFragment : Fragment() {

    private lateinit var binding: FragmentAllBinding
    private lateinit var adapter: YogaAdapter
    private var allYogaList = mutableListOf<YogaListX>()
    companion object{
        lateinit var allFragment: AllFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllBinding.inflate(layoutInflater, container, false)
        allFragment = this
        Utils.allYogaList.clear()
        Utils.allYogaList.addAll(Utils.beginnersList)
        Utils.allYogaList.addAll(Utils.intermediateList)
        allYogaList.addAll(Utils.allYogaList)
        binding.rvAll.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = YogaAdapter(allYogaList,requireContext())
        binding.rvAll.adapter = adapter

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchList(){
//        binding.rvAll.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        val adapter = YogaAdapter(Utils.yogaSearchList,requireContext())
        allYogaList.clear()
        allYogaList.addAll(Utils.yogaSearchList)
//        binding.rvAll.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}