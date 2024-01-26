package com.ark.nikestore.feature.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.dpToPx
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        //val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        binding.bottomNav.setupWithNavController(navController)

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemsCount()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount){

        val badge = binding.bottomNav.getOrCreateBadge(R.id.cart)
        badge.badgeGravity = BadgeDrawable.BOTTOM_START
        badge.backgroundColor = getColor(R.color.blue)
        badge.number = cartItemCount.count
        badge.verticalOffset = dpToPx(20)

        badge.isVisible = cartItemCount.count > 0
    }
}