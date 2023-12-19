package com.ark.nikestore.feature.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseCompletableObserver
import com.ark.nikestore.common.BaseFragment
import com.ark.nikestore.databinding.FragmentLoginBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    private val viewModel: AuthViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    override fun getLayoutRes() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(), "ایمیل و رمز عبور را وارد نمایید", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        Toast.makeText(requireContext(), "با موفقیت وارد شدید", Toast.LENGTH_SHORT).show()
                        requireActivity().finish()
                    }
                })
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }

        binding.signUpLinkBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, SignUpFragment())
            }.commit()
        }
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

}