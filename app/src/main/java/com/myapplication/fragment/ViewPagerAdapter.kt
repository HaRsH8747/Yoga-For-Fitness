package com.myapplication.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragmentList: List<Fragment> = ArrayList()
    private val mFragmentTitleList: List<String> = ArrayList()

    override fun getItemCount(): Int {
        Log.d("CLEAR","Inside size")
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                Log.d("CLEAR","Inside All")
                AllFragment()
            }
            1 -> {
                BeginnersFragment()
            }
            2 -> {
                IntermediateFragment()
            }
            3 -> {
                ExpertFragment()
            }
            4 -> {
                MyFavouritesFragment()
            }
            else -> {
                AllFragment()
            }
        }
    }
}