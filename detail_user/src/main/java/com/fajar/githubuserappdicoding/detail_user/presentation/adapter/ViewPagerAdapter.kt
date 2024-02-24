package com.fajar.githubuserappdicoding.detail_user.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fajar.githubuserappdicoding.detail_user.presentation.uiview.UserDetailInfoFragment

class ViewPagerAdapter(
    private val args: Bundle,
    fragmentManager: FragmentManager,
    viewLifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, viewLifecycle) {

    override fun createFragment(position: Int): Fragment {
        val fragment = UserDetailInfoFragment()
        fragment.arguments = args.apply {
            putInt(UserDetailInfoFragment.EXTRA_POSITION, position)
        }
        return fragment
    }

    override fun getItemCount() = 3

}