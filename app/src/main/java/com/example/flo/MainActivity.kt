package com.example.flo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()

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
}