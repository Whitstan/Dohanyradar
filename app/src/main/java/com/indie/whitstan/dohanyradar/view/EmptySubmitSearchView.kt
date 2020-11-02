package com.indie.whitstan.dohanyradar.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView

class EmptySubmitSearchView : SearchView {

    lateinit var mSearchSrcTextView: SearchAutoComplete
    internal var listener: OnQueryTextListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setOnQueryTextListener(listener: OnQueryTextListener?) {
        super.setOnQueryTextListener(listener)
        this.listener = listener
        mSearchSrcTextView = this.findViewById(androidx.appcompat.R.id.search_src_text)
        mSearchSrcTextView.setOnEditorActionListener { textView, i, keyEvent ->
            listener?.onQueryTextSubmit(query.toString())
            true
        }
    }
}