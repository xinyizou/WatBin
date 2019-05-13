package com.example.watbin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText

class SearchFragment : Fragment() {

    lateinit var recyclerView : RecyclerView
    private var adapter: ArrayAdapter<*>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.search, container, false)

        val searchField = view.findViewById<EditText>(R.id.search_field)

//        adapter = ArrayAdapter(this, R.layout.item_profile, names)
        searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val queryText = searchField.text.toString()
            }
        } )

        searchField.setOnEditorActionListener(DoneEditorActionListener())

        return view
    }

}