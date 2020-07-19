package sde.com.kotlinmultipleapps.CalcAgeApp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_age_calc_app.*
import sde.com.kotlinmultipleapps.R
import java.text.SimpleDateFormat
import java.util.*

class CalcAgeApp : AppCompatActivity() {

    private lateinit var selectedDate :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age_calc_app)

        btnSelectDate.setOnClickListener {view ->
            clickDatePicker(view)
        }
    }

    fun clickDatePicker(view: View){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                view, year, month, dayOfMonth ->
            Log.i("Date", "Year: $year Month: ${month+1} Day: $dayOfMonth" )
            selectedDate = "$dayOfMonth/${month+1}/$year"
            txtSelectedDate.text = selectedDate

            generateAge()
        }
            ,year,month,dayOfMonth)

        dpd.datePicker.maxDate = Date().time - 86400000
        dpd.show()
    }

    private fun generateAge() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val theDate = sdf.parse(selectedDate)

        val selectedDateInMinutes = theDate!!.time / 60000
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
        val currentDateInMin = currentDate!!.time / 60000

        val differenceInMinutes = currentDateInMin - selectedDateInMinutes
        txtAgeInMinutes.text = differenceInMinutes.toString()
    }
}