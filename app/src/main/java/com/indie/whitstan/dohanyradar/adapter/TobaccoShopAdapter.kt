package com.indie.whitstan.dohanyradar.adapter

import java.util.*

import kotlin.collections.ArrayList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.recyclerview.widget.RecyclerView

import com.indie.whitstan.dohanyradar.R
import com.indie.whitstan.dohanyradar.databinding.TobaccoShopRowBinding
import com.indie.whitstan.dohanyradar.model.TobaccoShop

class TobaccoShopAdapter(var mTobaccoShopList: List<TobaccoShop>) : RecyclerView.Adapter<TobaccoShopAdapter.TobaccoShopViewHolder>(), Filterable {

    var tobaccoShopFilterList : List<TobaccoShop>

    init {
        tobaccoShopFilterList = mTobaccoShopList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TobaccoShopViewHolder {
        return TobaccoShopViewHolder(
            TobaccoShopRowBinding.inflate(LayoutInflater.from(viewGroup.context),
                        viewGroup,
                        false)
        )
    }

    override fun onBindViewHolder(holder: TobaccoShopViewHolder, position: Int) {
        val shop =  tobaccoShopFilterList[position]
        holder.bind(shop)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                tobaccoShopFilterList = if (charSearch.isEmpty()) {
                    mTobaccoShopList
                } else {
                    val resultList = ArrayList<TobaccoShop>()
                    for (row in mTobaccoShopList) {
                        if (row.name.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = tobaccoShopFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                tobaccoShopFilterList = results?.values as ArrayList<TobaccoShop>
                notifyDataSetChanged()
            }
        }
    }

    inner class TobaccoShopViewHolder(private val binding: TobaccoShopRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shop: TobaccoShop) {
             binding.apply {
                tobaccoShop = shop
                executePendingBindings()
            }
        }

        init {
            binding.mainlayout.setOnClickListener{ view ->
                val args = bundleOf(
                    Pair("id",  binding.tobaccoShop!!.id),
                )
                val navController = view.findNavController()
                if (navController.currentDestination == navController.graph[R.id.tobacco_shops_list_destination]){
                    view.findNavController().navigate(R.id.navigate_to_tobacco_shop_details_from_list, args)
                }
                else if(navController.currentDestination == navController.graph[R.id.tobacco_shops_map_destination]){
                    view.findNavController().navigate(R.id.navigate_to_tobacco_shop_details_from_map, args)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tobaccoShopFilterList.size
    }

}