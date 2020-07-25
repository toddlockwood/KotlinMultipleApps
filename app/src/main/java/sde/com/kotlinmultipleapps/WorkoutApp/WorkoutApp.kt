package sde.com.kotlinmultipleapps.WorkoutApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_workout_app.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.R

class WorkoutApp : AppCompatActivityExtension() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_app)

        llStart.setOnClickListener {
            var intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
    }
}