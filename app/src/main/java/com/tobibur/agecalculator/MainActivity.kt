package com.tobibur.agecalculator

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_show_result.view.*
import java.time.LocalDate
import java.time.Period


class MainActivity : AppCompatActivity() {

    private lateinit var dateOfBirth: LocalDate
    private lateinit var tillDate: LocalDate

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dateOfBirth = LocalDate.now()
        tillDate = LocalDate.now()

        btnDob.setOnClickListener {
            btnDob.background = ContextCompat.getDrawable(this, R.drawable.border_button)
            btnTd.background = ContextCompat.getDrawable(this, R.drawable.round_button)
            switchCalendarViews()
        }

        btnTd.setOnClickListener {
            btnTd.background = ContextCompat.getDrawable(this, R.drawable.border_button)
            btnDob.background = ContextCompat.getDrawable(this, R.drawable.round_button)
            switchCalendarViews()
        }

        calendarDob.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            dateOfBirth = LocalDate.of(year, monthOfYear, dayOfMonth)
            txtDob.text = "Date of Birth: $dayOfMonth/${monthOfYear + 1}/$year"
        }

        calendarTd.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            tillDate = LocalDate.of(year, monthOfYear, dayOfMonth)
            txtTd.text = "Till date: $dayOfMonth/${monthOfYear + 1}/$year"
        }

        btnEvaluate.setOnClickListener {
            val age = getAge(dateOfBirth, tillDate)
            Log.d("MainActivity", "Age = $age")
            resultDialog(age)
        }
    }

    private fun switchCalendarViews() {
        if (viewSwitcher.currentView != calendarDob)
            viewSwitcher.showPrevious()
        else if (viewSwitcher.currentView != calendarTd)
            viewSwitcher.showNext()
    }

    private fun getAge(startDate: LocalDate, endDate: LocalDate): String {

        val p = Period.between(startDate, endDate)

        return "You are " + p.years + " years, " + p.months +
                " months and " + p.days +
                " days old."
    }

    private fun resultDialog(msg: String) {
        val inflater = layoutInflater
        val alertLayout = inflater.inflate(R.layout.dialog_show_result, null)
        val dialog = AlertDialog.Builder(this)
            //.setTitle("Invite Friend")
            .setView(alertLayout)
            //.setMessage("Invite your friend to join you..")
            .setCancelable(true)
            .show()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertLayout.dialog_message.text = msg

    }
}
