package com.kelompoktmr.wesata.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.kelompoktmr.wesata.R
import com.kelompoktmr.wesata.databinding.ActivityMainBinding
import com.kelompoktmr.wesata.fragments.HomeFragment
import com.kelompoktmr.wesata.fragments.NotificationsFragment
import com.kelompoktmr.wesata.fragments.SettingsFragment
import com.kelompoktmr.wesata.fragments.WishlistFragment
import com.kelompoktmr.wesata.services.NotificationService

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AndroidNetworking.initialize(getApplicationContext())

        val notificationService = Intent(this, NotificationService::class.java)
        startService(notificationService)

        val fragmenIntent = intent.getStringExtra("frag").toString()
        when (fragmenIntent) {
            "notificationFragment" -> setFragment(NotificationsFragment())
            else -> setFragment(HomeFragment())
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    setFragment(HomeFragment())
                    true
                }

                R.id.navigation_notifications -> {
                    setFragment(NotificationsFragment())
                    true
                }

                R.id.navigation_settings -> {
                    setFragment(SettingsFragment())
                    true
                }

                R.id.navigation_whistlist -> {
                    setFragment(WishlistFragment())
                    true
                }

                else -> false
            }
        }
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}