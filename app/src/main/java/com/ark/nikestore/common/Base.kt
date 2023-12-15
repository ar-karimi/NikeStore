package com.ark.nikestore.common

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ark.nikestore.R
import io.reactivex.disposables.CompositeDisposable
import java.util.Locale

abstract class BaseActivity : AppCompatActivity(), BaseView {
    override val rootView: ViewGroup?
        get() = window.decorView.rootView as ViewGroup
    override val viewContext: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setAppLocale("fa")
    }

    override fun onResume() {
        super.onResume()
        setAppLocale("fa")
    }

    private fun setAppLocale(localeKeyword: String) {
        val locale = Locale(localeKeyword)
        Locale.setDefault(locale)
        //val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), BaseView {
    override val rootView: ViewGroup?
        get() = view as ViewGroup
    override val viewContext: Context?
        get() = requireContext()

    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.lifecycleOwner = viewLifecycleOwner
    }

    abstract fun getLayoutRes(): Int
}

interface BaseView {
    val rootView: ViewGroup?
    val viewContext: Context?

    fun showProgressBar(mustShow: Boolean) {

        rootView?.let { rootView ->
            viewContext?.let { context ->

                var loadingView = rootView.findViewById<View>(R.id.loadingView)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.view_loading, rootView, false)
                    rootView.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }


}

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}