package sde.com.kotlinmultipleapps.WorkoutApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_finish.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.R
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivityExtension() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        setSupportActionBar(toolbar_finish_activity)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish()
        }
        addDateToDB()

    }

    private fun addDateToDB(){
        val calendar = Calendar.getInstance()
        val datetime = calendar.time
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(datetime)

        val dbHelper = SQLiteOpenHelper(this,null)
        dbHelper.addDate(date)
    }
}