package com.ark.nikestore.feature.auth

import android.os.Bundle
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity<ActivityAuthBinding>(R.layout.activity_auth) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, LoginFragment())
        }.commit()
    }
}