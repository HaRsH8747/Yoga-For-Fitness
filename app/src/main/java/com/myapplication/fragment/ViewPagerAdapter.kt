package com.myapplication.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myapplication.utils.Utils

class ViewPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    private val mFragmentList: List<Fragment> = ArrayList()
    private val mFragmentTitleList: List<String> = ArrayList()

//    override fun getItemCount(): Int {
//        return (Utils.categoryResponse.type.size + 2)
//    }

//    override fun createFragment(position: Int): Fragment {
//        return if (position == 0){
//            AllFragment()
//        }else if (position == Utils.categoryResponse.type.size + 1){
//            MyFavouritesFragment()
//        }
//        else if (position != 0 && position != (Utils.categoryResponse.type.size + 2)){
//            Log.d("CLEAR","pos: $position")
//            BeginnersFragment(Utils.categoryResponse.type[position-1].id)
//        } else{
//            AllFragment()
//        }
////        return when (position) {
////            0 -> {
////                AllFragment(Utils.allYogaList)
////            }
////            1 -> {
////                AllFragment(Utils.beginnersList)
////            }
////            2 -> {
////                AllFragment(Utils.intermediateList)
////            }
////            3 -> {
////                ExpertFragment()
////            }
////            4 -> {
////                MyFavouritesFragment()
////            }
////            else -> {
////                AllFragment(Utils.allYogaList)
////            }
////        }
//    }

    override fun getCount(): Int {
        return (Utils.categoryResponse.type.size + 2)
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            AllFragment()
        }else if (position == Utils.categoryResponse.type.size + 1){
            MyFavouritesFragment()
        }
        else if (position != 0 && position != (Utils.categoryResponse.type.size + 2)){
            Log.d("CLEAR","pos: $position")
            BeginnersFragment(Utils.categoryResponse.type[position-1].id)
        } else{
            AllFragment()
        }
    }
}