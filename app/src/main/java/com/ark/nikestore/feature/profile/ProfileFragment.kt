package com.ark.nikestore.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseFragment
import com.ark.nikestore.databinding.FragmentProfileBinding
import com.ark.nikestore.feature.auth.AuthActivity
import com.ark.nikestore.feature.favorites.FavoriteProductsActivity
import com.ark.nikestore.feature.order.OrderHistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModel()
    override fun getLayoutRes() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteProductsActivity::class.java))
        }

        binding.ordersHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistoryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        checkAuthState()
    }

    private fun checkAuthState() {
        if (viewModel.isSignedIn) {
            binding.authBtn.text = getString(R.string.signOut)
            binding.authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_out, 0)
            binding.authBtn.setOnClickListener {
                viewModel.signOut()
                checkAuthState()
            }
            binding.userNameTv.text = viewModel.userName
        } else {
            binding.authBtn.text = getString(R.string.signIn)
            binding.authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_in, 0)
            binding.authBtn.setOnClickListener {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
            binding.userNameTv.text = getString(R.string.guestUser)
        }
    }
}