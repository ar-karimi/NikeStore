package com.ark.nikestore.feature.auth

import android.os.Bundle
import android.view.View
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseCompletableObserver
import com.ark.nikestore.common.BaseFragment
import com.ark.nikestore.databinding.FragmentSignUpBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment: BaseFragment<FragmentSignUpBinding>() {

    private val viewModel: AuthViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    override fun getLayoutRes() = R.layout.fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                showToast("ایمیل و رمز عبور را وارد نمایید")
                return@setOnClickListener
            }
            viewModel.signUp(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseCompletableObserver(compositeDisposable){
                    override fun onComplete() {
                        showToast("با موفقیت وارد شدید")
                        requireActivity().finish()
                    }
                })
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }

        binding.loginLinkBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, LoginFragment())
            }.commit()
        }

    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}