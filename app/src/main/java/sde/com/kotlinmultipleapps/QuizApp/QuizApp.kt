package sde.com.kotlinmultipleapps.QuizApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz_app.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.CalcAgeApp.CalcAgeApp
import sde.com.kotlinmultipleapps.R

class QuizApp : AppCompatActivityExtension() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_app)

        btn_start.setOnClickListener {
            if(etName.text.toString().isEmpty()){
                Toast.makeText(this,"Hey! Please enter your name!",Toast.LENGTH_LONG).show()

            }
            else{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, etName.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}