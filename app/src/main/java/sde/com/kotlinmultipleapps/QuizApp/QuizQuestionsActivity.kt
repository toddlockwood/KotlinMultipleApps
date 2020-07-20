package sde.com.kotlinmultipleapps.QuizApp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.R

class QuizQuestionsActivity : AppCompatActivityExtension() , OnClickListener{

    private var mCurrentPos : Int = 1
    private var mQuestList :ArrayList<Question>? = null
    private var mSelctedOption : Int = 0
    private var mCorrectAnswers : Int = 0
    private var mUserName : String? = null
    lateinit private var question : Question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mQuestList = Constants.getQuestions()
        mCurrentPos = 1
        question = mQuestList!![mCurrentPos-1]

        setQuestion(mCurrentPos, question)
        setMultipleOnClickListeners()

    }

    private fun setMultipleOnClickListeners() {
        tvQuestionOne.setOnClickListener(this)
        tvQuestionTwo.setOnClickListener(this)
        tvQuestionThree.setOnClickListener(this)
        tvQuestionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, tvQuestionOne)
        options.add(1, tvQuestionTwo)
        options.add(2, tvQuestionThree)
        options.add(3, tvQuestionFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }

    }

    private fun setQuestion(
        mCurrentPos: Int,
        question: Question?
    ) {
        defaultOptionsView()
        setProgressBar(mCurrentPos)
        setCurrentPos(mCurrentPos)
        setQuestion(question!!.question)
        setImage(question!!.image)
        setOption(1, question!!.optOne)
        setOption(2, question!!.optTwo)
        setOption(3, question!!.optThree)
        setOption(4, question!!.optFour)
        if (mCurrentPos == mQuestList!!.size){
            btnSubmit.text = "FINISH"
        }else{
            btnSubmit.text = "Submit"
        }
    }

    private fun setOption(i: Int, option: String) {
        when(i) {
            1 -> tvQuestionOne.text = option
            2 -> tvQuestionTwo.text = option
            3 -> tvQuestionThree.text = option
            4 -> tvQuestionFour.text = option
        }
    }

    private fun setImage(image: Int) {
        imgQuestion.setImageResource(image)
    }

    private fun setQuestion(question: String) {
        tvQuestion.text = question
    }

    private fun setCurrentPos(currentPos: Int) {
        tvProgress.text = "$currentPos/${progressBar.max}"
    }

    private fun setProgressBar(currentPos: Int) {
        progressBar.progress = currentPos
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvQuestionOne -> {
                selectedOptionView(tvQuestionOne,1)
            }
            R.id.tvQuestionTwo ->{
                selectedOptionView(tvQuestionTwo,2)
            }
            R.id.tvQuestionThree->{
                selectedOptionView(tvQuestionThree,3)
            }
            R.id.tvQuestionFour->{
                selectedOptionView(tvQuestionFour,4)
            }
            R.id.btnSubmit->{
                goSubmit()
            }
        }
    }

    private fun goSubmit() {
        if (mSelctedOption == 0) {
            mCurrentPos++
            when {
                mCurrentPos <= mQuestList!!.size -> {
                    setQuestion(mCurrentPos, mQuestList!![mCurrentPos - 1])
                }
                else -> {
                    val intent = Intent(this, ResultQuizActivity::class.java)
                    intent.putExtra(Constants.USER_NAME,mUserName)
                    intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                    intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestList!!.size)
                    startActivity(intent)
                    finish()
                }
            }
        } else {
            val question = mQuestList!!.get(mCurrentPos - 1)
            if (question!!.corAns != mSelctedOption) {
                ansverView(mSelctedOption, R.drawable.wrong_option_border_bg)
            }else{
                mCorrectAnswers++
            }
            ansverView(question.corAns, R.drawable.correct_option_border_bg)
            if(mCurrentPos ==mQuestList!!.size){
                btnSubmit.text = "FINISH"
            }else{
                btnSubmit.text= "Go to next Question"
            }
            mSelctedOption=0
        }
    }

    private fun ansverView(answer: Int, drawableView:Int){
        when(answer){
            1 -> tvQuestionOne.background = ContextCompat.getDrawable(this, drawableView)
            2 -> tvQuestionTwo.background = ContextCompat.getDrawable(this, drawableView)
            3 -> tvQuestionThree.background = ContextCompat.getDrawable(this, drawableView)
            4 -> tvQuestionFour.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun selectedOptionView(tv:TextView, selectedOptNum : Int){
        defaultOptionsView()
        mSelctedOption = selectedOptNum

        tv.setTextColor(Color.parseColor("#000000"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)
    }
}