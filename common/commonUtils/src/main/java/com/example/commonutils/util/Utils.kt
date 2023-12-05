package com.example.commonutils.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import java.util.regex.Pattern

fun Fragment.hideSoftKeyboard() {
    requireActivity().currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

@SuppressLint("ClickableViewAccessibility")
fun Fragment.setupUI(view: View) {

    // Set up touch listener for non-text box views to hide keyboard.
    if (view !is EditText) {
        view.setOnTouchListener { v, event ->
            hideSoftKeyboard()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setupUI(innerView)
        }
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun getFileSize(uri: Uri?, context: Context): Long {
    if (uri != null) {
        return context.contentResolver.openAssetFileDescriptor(uri, "r")!!.length
    }
    return 0
}

fun Fragment.orientationPortrait() {
    requireActivity()
        .requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Fragment.orientationLandscape() {
    requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

fun TextInputEditText.textChange(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(text: Editable?) {
            afterTextChanged.invoke(text.toString())
        }
    })
}

fun AutoCompleteTextView.textChange(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(text: Editable?) {
            afterTextChanged.invoke(text.toString())
        }
    })
}

fun Fragment.pickDate(
    _day: Int,
    _month: Int,
    _year: Int,
    _date: (Int, Int, Int) -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        requireContext(),
        { view, year, monthOfYear, dayOfMonth ->
            _date.invoke(year, monthOfYear, dayOfMonth)
        },
        _year,
        _month,
        _day
    )
    datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
    datePickerDialog.show()
}

fun Fragment.pickTime(
    _hour: Int,
    _minute: Int,
    is24Hour: Boolean,
    _time: (hourOfDay: Int, minute: Int) -> Unit
) {
    val timePickerDialog = TimePickerDialog(
        requireContext(),
        { view, hourOfDay, minute ->
            _time.invoke(hourOfDay, minute)
        },
        _hour,
        _minute,
        is24Hour
    )
    timePickerDialog.show()
}

fun isNotNullOrZero(number: String, errorMessage: String): String? {
    if (number.trim().isNullOrEmpty() || number.trim() == "0") {
        return errorMessage
    }
    return null
}

fun isValidName(name: String, errorMessage: String = ""): String? {
    if (name.trim().isNullOrEmpty()) {
        return errorMessage
    }
    return null
}

fun isValidEmail(email: String, errorMessage: String = ""): String? {
    if (email.trim().isNullOrEmpty()) {
        return errorMessage
    } else if (!Pattern.compile("[a-zA-Z0-9+_.-]+@[a-zA-Z0-9]+[.-][a-zA-Z][a-z.A-Z]+")
            .matcher(email)
            .matches()
    ) {
        return errorMessage
    }
    return null
}

fun isValidMobileNumber(number: String, errorMessage: String): String? {
    if (number.trim().isNullOrEmpty()) {
        return errorMessage
    } else if (number.trim().length != 10) {
        return errorMessage
    } else if (!number.trim().matches("^[6-9][0-9]{9}$".toRegex())) {
        return errorMessage
    }
    return null
}

fun isValidNumber(number: String, errorMessage: String): String? {
    if (number.trim().isNullOrEmpty()) {
        return errorMessage
    } else if (number.trim().length != 10) {
        return errorMessage
    }
    return null
}

fun Fragment.toastMsg(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}
fun Activity.toastMsg(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}