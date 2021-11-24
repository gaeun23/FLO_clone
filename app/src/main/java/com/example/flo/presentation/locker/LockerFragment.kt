package com.example.flo.presentation.locker

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.presentation.main.MainActivity
import com.example.flo.presentation.sign.SignInActivity
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private val lockerViewPagerText = arrayListOf("좋아요", "음악파일", "좋아요한 앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)
        setViewPagerAdapter()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initSignView()
    }

    private fun setViewPagerAdapter() {
        val lockerAdapter = LockerViewPagerAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        binding.lockerContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) { tab, position ->
            tab.text = lockerViewPagerText[position]
        }.attach()
    }

    private fun initSignView() {
        val jwt = getJwt()

        when (jwt == 0) {
            true -> {
                binding.lockerTextSignTv.text = "로그인"
                binding.lockerTextSignTv.setOnClickListener {
                    startActivity(Intent(context, SignInActivity::class.java))
                }
            }
            else -> {
                binding.lockerTextSignTv.text = "로그아웃"
                binding.lockerTextSignTv.setOnClickListener {
                    startActivity(Intent(context, MainActivity::class.java))
                    Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
                    setLogout()
                }
            }
        }
    }

    private fun getJwt(): Int {
        val sharedPreferences = activity?.getSharedPreferences("auth", MODE_PRIVATE)
        return sharedPreferences!!.getInt("jwt", 0)
    }

    private fun setLogout() {
        val sharedPreferences = activity?.getSharedPreferences("auth", MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()

        editor.remove("jwt")
        editor.apply()
    }
}
