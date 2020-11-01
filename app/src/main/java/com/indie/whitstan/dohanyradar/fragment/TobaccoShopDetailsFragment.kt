package com.indie.whitstan.dohanyradar.fragment

import kotlinx.android.synthetic.main.fragment_tobacco_shop_details.*

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.indie.whitstan.dohanyradar.R
import com.indie.whitstan.dohanyradar.databinding.FragmentTobaccoShopDetailsBinding
import com.indie.whitstan.dohanyradar.model.TobaccoShopDetails
import com.indie.whitstan.dohanyradar.network.RetrofitClient
import com.indie.whitstan.dohanyradar.utils.BASE_URL

class TobaccoShopDetailsFragment : Fragment() {
    private lateinit var binding : FragmentTobaccoShopDetailsBinding

    private var mReconnectButton : Button? = null
    private var mTobaccoShopDetailsLayout : ConstraintLayout? = null
    private var mTobaccoShopDetailsProgressBar : ContentLoadingProgressBar? = null

    private var mRetrofitClient : RetrofitClient = RetrofitClient()

    private val args: TobaccoShopDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tobacco_shop_details,
            container,
            false
        )
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        mReconnectButton = binding.reconnectButtonDetails
        mTobaccoShopDetailsLayout = binding.layoutTobaccoShopDetails
        mTobaccoShopDetailsProgressBar = binding.progressBarTobaccoShopDetails
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shopId = args.id
        mReconnectButton?.setOnClickListener{loadTobaccoShopDetailsData(shopId)}
        loadTobaccoShopDetailsData(shopId)
    }

    private fun loadTobaccoShopDetailsData(id : Int) {
        showLoadingIndicator()
        mRetrofitClient.createTobaccoShopListCallById(id)?.enqueue(object : Callback<TobaccoShopDetails?> {
            override fun onResponse(call: Call<TobaccoShopDetails?>, response: Response<TobaccoShopDetails?>) {
                hideLoadingIndicator()
                hideReconnectButton()
                response.body()?.let {
                    binding.tobaccoShopDetails = it
                    loadImageByUrl(BASE_URL + "tobbacoshop/" + it.id + "/image")
                }
            }

            override fun onFailure(call: Call<TobaccoShopDetails?>, error: Throwable) {
                hideLoadingIndicator()
                showReconnectButton()
                Toast.makeText(context, R.string.error_while_loading_tobacco_shop_details, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        })
    }

    private fun loadImageByUrl(url: String){
        Glide.with(requireContext())
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(tobacco_shop_image)
    }

    private fun showReconnectButton(){
        mReconnectButton?.visibility = View.VISIBLE
    }

    private fun hideReconnectButton(){
        mReconnectButton?.visibility = View.GONE
    }

    private fun showLoadingIndicator() {
        mTobaccoShopDetailsLayout?.visibility = View.GONE
        mTobaccoShopDetailsProgressBar?.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        mTobaccoShopDetailsLayout?.visibility = View.VISIBLE
        mTobaccoShopDetailsProgressBar?.visibility = View.GONE
    }
}