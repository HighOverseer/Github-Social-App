package com.fajar.githubuserappdicoding.presentation.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fajar.githubuserappdicoding.presentation.uiview.UserDetailInfoFragment

class ViewPagerAdapter(
    private val args: Bundle,
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = UserDetailInfoFragment()
        fragment.arguments = args.apply {
            putInt(UserDetailInfoFragment.EXTRA_POSITION, position)
        }
        return fragment
    }

    override fun getItemCount() = 3

}