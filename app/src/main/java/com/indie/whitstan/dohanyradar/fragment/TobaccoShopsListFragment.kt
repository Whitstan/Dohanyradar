package com.indie.whitstan.dohanyradar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.indie.whitstan.dohanyradar.R
import com.indie.whitstan.dohanyradar.adapter.TobaccoShopAdapter
import com.indie.whitstan.dohanyradar.databinding.FragmentTobaccoShopsListBinding
import com.indie.whitstan.dohanyradar.model.TobaccoShop
import com.indie.whitstan.dohanyradar.network.RetrofitClient

class TobaccoShopsListFragment : Fragment(){
    private lateinit var binding: FragmentTobaccoShopsListBinding
    private var mAdapter: TobaccoShopAdapter? = null
    private var mReconnectButton : Button? = null
    private var mSearchView : SearchView? = null
    private var mRetrofitClient: RetrofitClient = RetrofitClient()
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onResume() {
        super.onResume()
        resetSearchViewText()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tobacco_shops_list,
            container,
            false
        )
        setupViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        loadTobaccoShopDataWithoutQueryString()
    }

    private fun setupViews() {
        mSearchView = binding.tobaccoShopSearchView
        mReconnectButton = binding.reconnectButtonMain
        mSwipeRefreshLayout = binding.swipeRefreshLayout
        mRecyclerView = binding.tobaccoShopsRecycleView
        mRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun setupListeners(){
        mSwipeRefreshLayout.setOnRefreshListener {loadTobaccoShopDataWithoutQueryString()}

        mReconnectButton?.setOnClickListener {loadTobaccoShopDataWithoutQueryString()}

        mSearchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { loadTobaccoShopDataWithQueryString(it) }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter?.filter?.filter(newText)
                return false
            }
        })
    }

    private fun loadTobaccoShopDataWithQueryString(queryString : String) {
        loadTobaccoShopData(mRetrofitClient.createTobaccoShopListCallByQueryString(queryString))
    }

    private fun loadTobaccoShopDataWithoutQueryString() {
        loadTobaccoShopData(mRetrofitClient.createTobaccoShopListCall())
    }

    private fun loadTobaccoShopData(call : Call<List<TobaccoShop>?>?) {
        showLoadingIndicator()
        call?.enqueue(object : Callback<List<TobaccoShop>?> {
            override fun onResponse(call: Call<List<TobaccoShop>?>, response: Response<List<TobaccoShop>?>) {
                hideLoadingIndicator()
                hideReconnectButton()
                mAdapter = response.body()?.let {
                    TobaccoShopAdapter(it)
                }!!
                mRecyclerView.adapter = mAdapter
            }

            override fun onFailure(call: Call<List<TobaccoShop>?>, error: Throwable) {
                if (mRecyclerView.adapter == null || mAdapter?.mTobaccoShopList?.isEmpty()!!){
                    showReconnectButton()
                }
                hideLoadingIndicator()
                Toast.makeText(context, R.string.error_while_loading_tobacco_shop_list, Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        })
    }

    private fun showReconnectButton(){
        mReconnectButton?.visibility = View.VISIBLE
    }

    private fun hideReconnectButton(){
        mReconnectButton?.visibility = View.GONE
    }

    private fun showLoadingIndicator() {
        mSwipeRefreshLayout.isRefreshing = true
        mRecyclerView.visibility = View.GONE
    }

    private fun hideLoadingIndicator() {
        mSwipeRefreshLayout.isRefreshing = false
        mRecyclerView.visibility = View.VISIBLE
    }

    private fun resetSearchViewText(){
        mSearchView?.setQuery("", false);
    }
}