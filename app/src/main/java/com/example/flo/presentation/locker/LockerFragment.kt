package com.example.flo.presentation.locker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.presentation.main.SongActivity
import com.example.flo.presentation.sign.SignInActivity
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private val lockerViewPagerText = arrayListOf("좋아요", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)
        setViewPagerAdapter()
        setSignBtnClickListener()
        return binding.root
    }

    private fun setViewPagerAdapter() {
        val lockerAdapter = LockerViewPagerAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        binding.lockerContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) { tab, position ->
            tab.text = lockerViewPagerText[position]
        }.attach()
    }

    private fun setSignBtnClickListener() {
        binding.lockerTextSignTv.setOnClickListener {
            activity?.let {
                startActivity(Intent(context, SignInActivity::class.java))
            }
        }
    }
}
