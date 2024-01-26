package com.ark.nikestore.common

import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.res.ResourcesCompat
import com.ark.nikestore.R
import com.ark.nikestore.feature.auth.AuthActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

interface BaseView {

    val rootView: ViewGroup

    fun showProgressBar(mustShow: Boolean) {

        var loadingView = rootView.findViewById<View>(R.id.loadingView)
        if (loadingView == null && mustShow) {
            loadingView = LayoutInflater.from(rootView.context)
                .inflate(R.layout.view_loading, rootView, false)
            rootView.addView(loadingView)
        }
        loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
    }

    /*fun showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(rootView, message, duration).show()
    }*/

    fun showToast(message: String) {

        rootView.context.let { context ->
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
    fun showError(baseException: BaseException) {
        rootView.context.let {
            when (baseException.type) {
                BaseException.Type.SIMPLE -> showToast(
                    baseException.serverMessage ?: it.getString(baseException.userFriendlyMessage)
                )

                BaseException.Type.AUTH -> {
                    showToast(baseException.serverMessage!!)
                    it.startActivity(Intent(it, AuthActivity::class.java))
                }

                BaseException.Type.DIALOG -> {}
            }
        }
    }

    fun showEmptyState(@LayoutRes layoutResId: Int): View? {

        var emptyState = rootView.findViewById<View>(R.id.emptyStateRootView)
        if (emptyState == null) {
            emptyState = LayoutInflater.from(rootView.context).inflate(layoutResId, rootView, false)
            rootView.addView(emptyState)
        }
        emptyState.visibility = View.VISIBLE
        return emptyState
    }

}