package com.ark.nikestore.feature.auth

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity() {

    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        binding.lifecycleOwner = this



    }
}