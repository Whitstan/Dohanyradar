package com.indie.whitstan.dohanyradar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.indie.whitstan.dohanyradar.R
import com.indie.whitstan.dohanyradar.databinding.FragmentTobaccoShopDetailsBinding
import com.indie.whitstan.dohanyradar.model.TobaccoShop
import com.indie.whitstan.dohanyradar.model.TobaccoShopDetails
import com.indie.whitstan.dohanyradar.network.RetrofitClient
import kotlinx.android.synthetic.main.fragment_tobacco_shop_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TobaccoShopDetailsFragment : Fragment() {
    private lateinit var binding : FragmentTobaccoShopDetailsBinding
    private var mRetrofitClient : RetrofitClient = RetrofitClient()

    private val args: TobaccoShopDetailsFragmentArgs by navArgs()

    private fun loadTobaccoShopDetailsData(id : Int) {
        showLoadingIndicator()
        mRetrofitClient.createTobaccoShopListCallById(id)?.enqueue(object : Callback<TobaccoShopDetails?> {
            override fun onResponse(call: Call<TobaccoShopDetails?>, response: Response<TobaccoShopDetails?>) {
                hideLoadingIndicator()
                response.body()?.let {
                    binding.tobaccoShopDetails = it
                }
            }

            override fun onFailure(call: Call<TobaccoShopDetails?>, error: Throwable) {
                hideLoadingIndicator()
                Toast.makeText(context, R.string.error_while_loading_tobacco_shop_details, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tobacco_shop_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFieldsData(args)
        loadTobaccoShopDetailsData(args.id)
    }

    private fun setupFieldsData(data: TobaccoShopDetailsFragmentArgs){
       // mTitleText.text = data.title
    }

    private fun showLoadingIndicator() {
        layoutTobaccoShopDetails.visibility = View.GONE
        progress_bar_tobacco_shop_details.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        layoutTobaccoShopDetails.visibility = View.VISIBLE
        progress_bar_tobacco_shop_details.visibility = View.GONE
    }
}