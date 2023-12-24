package com.ark.nikestore.feature.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseCompletableObserver
import com.ark.nikestore.common.BaseFragment
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.data.CartItem
import com.ark.nikestore.databinding.FragmentCartBinding
import com.ark.nikestore.feature.product.ProductDetailActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment: BaseFragment<FragmentCartBinding>(), CartAdapter.CartItemViewCallbacks {

    private val viewModel: CartViewModel by viewModel()
    private lateinit var cartAdapter: CartAdapter
    private val compositeDisposable = CompositeDisposable()
    override fun getLayoutRes() = R.layout.fragment_cart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCartItemsLiveData().observe(viewLifecycleOwner){
            cartAdapter = CartAdapter(it as MutableList<CartItem>, this)
            binding.cartItemsRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            binding.cartItemsRv.adapter = cartAdapter
        }

        viewModel.getPurchaseDetailLiveData().observe(viewLifecycleOwner){
            cartAdapter?.let {cartAdapter ->
                cartAdapter.purchaseDetail = it
                cartAdapter.notifyItemChanged(cartAdapter.cartItemList.size)
            }
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.refresh()
    }

    override fun onRemoveItemClick(cartItem: CartItem) {
        viewModel.removeItemFromCart(cartItem)
            .subscribe(object : BaseCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter.removeCartItem(cartItem)
                }
            })
    }

    override fun onIncreaseItemClick(cartItem: CartItem) {
        viewModel.increaseCartItemCount(cartItem)
            .subscribe(object : BaseCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter.increaseOrDecreaseCount(cartItem)
                }
            })
    }

    override fun onDecreaseItemClick(cartItem: CartItem) {
        viewModel.decreaseCartItemCount(cartItem)
            .subscribe(object : BaseCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    cartAdapter.increaseOrDecreaseCount(cartItem)
                }
            })
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, cartItem.product)
        })
    }
}