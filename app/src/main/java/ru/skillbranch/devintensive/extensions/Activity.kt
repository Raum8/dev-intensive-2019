package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleRect = Rect()
    this.findViewById<View>(android.R.id.content).getWindowVisibleDisplayFrame(visibleRect)
    val heightDiff = findViewById<View>(android.R.id.content).height - (visibleRect.bottom - visibleRect.top)
    return heightDiff >= 100
}

fun Activity.isKeyboardClosed(): Boolean = !this.isKeyboardOpen()

