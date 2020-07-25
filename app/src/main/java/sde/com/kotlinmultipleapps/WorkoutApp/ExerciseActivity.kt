package sde.com.kotlinmultipleapps.WorkoutApp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import kotlinx.android.synthetic.main.activity_exercise.*
import sde.com.kotlinmultipleapps.R

class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var progressDuration = 10

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseProgressDuration = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        exerciseList = Constants.defaultExerciseList()
        setUpRestView()

    }

    private fun setRestProgressBar() {
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer((progressDuration * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = progressDuration - restProgress
                tvTimer.text = (progressDuration - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExerciseView()
            }
        }.start()

    }

    private fun setExerciseProgressBar() {
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer((exerciseProgressDuration * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = exerciseProgressDuration - exerciseProgress
                tvExerciseTimer.text = (exerciseProgressDuration - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList!!.size - 1) {
                    setUpRestView()
                } else {

                }
            }
        }.start()

    }

    private fun setUpExerciseView() {
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    private fun setUpRestView() {
        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
        }

        super.onDestroy()

    }
}