package sde.com.kotlinmultipleapps.QuizApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result_quiz.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.R

class ResultQuizActivity : AppCompatActivityExtension() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_quiz)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        tv_name.text = userName

        val points = intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        val totalQuest = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        tv_score.text = "Your score is $points/$totalQuest"

        btn_finish.setOnClickListener(){
            startActivity(Intent(this, QuizApp::class.java))
            finish()
        }
    }
}