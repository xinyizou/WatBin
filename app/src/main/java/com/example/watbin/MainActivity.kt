package com.example.watbin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var toolbar : ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val homeFragment = HomeFragment()
        openFragment(homeFragment)

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar.title = "WatBin"
                val homeFragment = HomeFragment()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                toolbar.title = "Search"
                val searchFragment = SearchFragment()
                openFragment(searchFragment)
//                startSearchActivity()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_camera -> {
                toolbar.title = "Camera"
//                val cameraFragment = CameraFragment()
//                openFragment(cameraFragment)
                startSearchActivity()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun startSearchActivity() {
        val intent = Intent(this, CameraActivity::class.java).apply {}
        startActivity(intent)
    }
}