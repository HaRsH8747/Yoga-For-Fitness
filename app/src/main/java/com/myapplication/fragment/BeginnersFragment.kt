package com.myapplication.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myapplication.adapter.YogaAdapter
import com.myapplication.databinding.FragmentBeginnersBinding
import com.myapplication.models.YogaListX
import com.myapplication.utils.Utils

class BeginnersFragment : Fragment() {

    private lateinit var binding: FragmentBeginnersBinding
    private lateinit var adapter: YogaAdapter
    private var beginnersList = mutableListOf<YogaListX>()
    companion object{
        lateinit var beginnersFragment: BeginnersFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBeginnersBinding.inflate(layoutInflater, container, false)
        beginnersFragment = this
        beginnersList.addAll(Utils.beginnersList)
        binding.rvBeginner.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = YogaAdapter(beginnersList, requireContext())
        binding.rvBeginner.adapter = adapter

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSearchList(){
//        binding.rvAll.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        val adapter = YogaAdapter(Utils.yogaSearchList,requireContext())
//        binding.rvAll.adapter = adapter
        beginnersList.clear()
        beginnersList.addAll(Utils.yogaSearchList)
        adapter.notifyDataSetChanged()
    }
}