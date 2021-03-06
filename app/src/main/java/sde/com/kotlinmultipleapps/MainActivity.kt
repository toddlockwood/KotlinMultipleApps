package sde.com.kotlinmultipleapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import sde.com.kotlinmultipleapps.CalcAgeApp.CalcAgeApp
import sde.com.kotlinmultipleapps.CalcApp.CalcApp
import sde.com.kotlinmultipleapps.HappyPlacesApp.activities.HappyPlacesApp
import sde.com.kotlinmultipleapps.PaintApp.PaintActivity
import sde.com.kotlinmultipleapps.QuizApp.QuizApp
import sde.com.kotlinmultipleapps.WorkoutApp.WorkoutApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        btnAgeApp.setOnClickListener {
            val intent = Intent(this, CalcAgeApp::class.java)
            startActivity(intent)
        }

        btnCalcApp.setOnClickListener {
            val intent = Intent(this, CalcApp::class.java)
            startActivity(intent)
        }

        btnQuizApp.setOnClickListener {
            val intent = Intent(this, QuizApp::class.java)
            startActivity(intent)
        }

        btnPaintApp.setOnClickListener {
            val intent = Intent(this, PaintActivity::class.java)
            startActivity(intent)
        }

        btnWorkoutApp.setOnClickListener {
            val intent = Intent(this, WorkoutApp::class.java)
            startActivity(intent)
        }

        btnHappyPlApp.setOnClickListener {
            val intent = Intent(this, HappyPlacesApp::class.java)
            startActivity(intent)
        }
    }

}