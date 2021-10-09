package com.example.flo.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.presentation.home.HomeFragment
import com.example.flo.presentation.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        setPlayerClickListener()

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    setMainBnvTransaction(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.lookFragment -> {
                    setMainBnvTransaction(LookFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    setMainBnvTransaction(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    setMainBnvTransaction(LockerFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
    }

    private fun setMainBnvTransaction(thisFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, thisFragment)
            .commitAllowingStateLoss()
    }

    private fun setPlayerClickListener() {
        val song = Song(
            binding.mainMiniplayerTitleTv.text.toString(),
            binding.mainMiniplayerSingerTv.text.toString()
        )

        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            startActivity(intent)
        }
    }
}