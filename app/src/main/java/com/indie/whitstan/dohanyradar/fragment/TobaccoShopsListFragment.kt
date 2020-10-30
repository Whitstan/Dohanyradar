package com.indie.whitstan.dohanyradar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.indie.whitstan.dohanyradar.R
import com.indie.whitstan.dohanyradar.adapter.TobaccoShopAdapter
import com.indie.whitstan.dohanyradar.databinding.FragmentTobaccoShopsListBinding
import com.indie.whitstan.dohanyradar.model.TobaccoShop
import com.indie.whitstan.dohanyradar.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TobaccoShopsListFragment : Fragment() {
    private lateinit var binding: FragmentTobaccoShopsListBinding
    private var mAdapter: TobaccoShopAdapter? = null
    private var mRetrofitClient: RetrofitClient = RetrofitClient()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ContentLoadingProgressBar
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tobacco_shops_list,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
        loadTobaccoShopData()
    }

    private fun setupViews() {
        mRecyclerView = binding.tobaccoShopsRecycleView
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        mProgressBar = binding.progressBarTobaccoShopsList
        mSwipeRefreshLayout = binding.swipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener {loadTobaccoShopData()}
    }

    private fun loadTobaccoShopData() {
        showLoadingIndicator()
        mRetrofitClient.createTobaccoShopListCall()?.enqueue(object : Callback<List<TobaccoShop>?> {
            override fun onResponse(call: Call<List<TobaccoShop>?>, response: Response<List<TobaccoShop>?>) {
                hideLoadingIndicator()
                mAdapter = response.body()?.let {
                    TobaccoShopAdapter(it)
                }!!
                mRecyclerView.adapter = mAdapter
            }

            override fun onFailure(call: Call<List<TobaccoShop>?>, error: Throwable) {
                hideLoadingIndicator()
                Toast.makeText(context, R.string.error_while_loading_tobacco_shop_list, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        })
    }

    private fun showLoadingIndicator() {
        mRecyclerView.visibility = View.GONE
        mProgressBar.visibility = View.VISIBLE
        mSwipeRefreshLayout.isRefreshing = false
    }

    private fun hideLoadingIndicator() {
        mRecyclerView.visibility = View.VISIBLE
        mProgressBar.visibility = View.GONE
        mSwipeRefreshLayout.isRefreshing = false
    }
}