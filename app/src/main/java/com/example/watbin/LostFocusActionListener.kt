package com.example.watbin

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

class LostFocusActionListener : View.OnClickListener {
    override fun onClick(view: View?) {
        if (view !is EditText) {
            val imm = view?.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(), 0)
            view.clearFocus()
        }
    }
}