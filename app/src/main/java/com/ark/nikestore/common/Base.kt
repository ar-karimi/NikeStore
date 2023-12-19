package com.ark.nikestore.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ark.nikestore.R
import com.ark.nikestore.feature.auth.AuthActivity
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
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
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
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

    /*fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT){
        rootView?.let {
            Snackbar.make(it, message, duration).show()
        }
    }*/

    fun showToast(message: String) {
        viewContext?.let {context ->
            val toastView = carbon.widget.TextView(context)
            toastView.setBackgroundColor(context.getColor(R.color.toastBackground))
            toastView.setCornerRadius(context.resources.getDimension(com.intuit.sdp.R.dimen._7sdp))
            toastView.setPadding(
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._13sdp),
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp),
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._13sdp),
                context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._12sdp)
            )
            toastView.setTextColor(context.getColor(R.color.toastText))
            toastView.typeface = ResourcesCompat.getFont(context, R.font.primary_regular)
            toastView.text = message
            toastView.textDirection = View.TEXT_DIRECTION_LOCALE

            Toast(context).apply {
                duration = Toast.LENGTH_LONG
                setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 145)
                view = toastView
            }.show()
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(baseException: BaseException){
        viewContext?.let {
            when(baseException.type){
                BaseException.Type.SIMPLE -> showToast(
                    baseException.serverMessage ?: it.getString(baseException.userFriendlyMessage))

                BaseException.Type.AUTH -> {
                    showToast(baseException.serverMessage!!)
                    it.startActivity(Intent(it, AuthActivity::class.java))
                }
                BaseException.Type.DIALOG -> {}
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