package sde.com.kotlinmultipleapps.WorkoutApp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import org.w3c.dom.Text
import sde.com.kotlinmultipleapps.R
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener{

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var progressDuration = 1

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseProgressDuration = 1

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null
    private var mPlayer : MediaPlayer? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogobackBtn()
        }
        tts = TextToSpeech(this, this)
        exerciseList = Constants.defaultExerciseList()
        setUpRestView()
        setUpExerciseStatusRecyclerView()

    }

    private fun setUpExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter
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
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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
                if (currentExercisePosition < 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    setUpRestView()
                } else {
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)

                }
            }
        }.start()

    }

    @SuppressLint("NewApi")
    private fun setUpExerciseView() {
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressBar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    private fun setUpRestView() {
        setUpPlayer()

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }

    private fun setUpPlayer() {
        try {
            mPlayer = MediaPlayer.create(applicationContext, R.raw.press_start)
            mPlayer!!.isLooping = false
            mPlayer!!.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
        }

        if(tts !=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if (mPlayer!=null){
            mPlayer!!.stop()
        }

        super.onDestroy()

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("Error", "Lang. not supported")
            }
        }else{
            Log.e("Error", "Init failed")
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut(text: String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun customDialogobackBtn(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_custom_back_confirmation)
        dialog.tvYes.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        dialog.tvNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}