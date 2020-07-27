package sde.com.kotlinmultipleapps.WorkoutApp

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_exercise.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.R
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivityExtension() {

    val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    val US_UNIT_VIEW = "US_UNIT_VIEW"

    var currentVisibleView = METRIC_UNIT_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Calculate BMI"
        }

        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if (currentVisibleView == METRIC_UNIT_VIEW){
                if (validateMetricUnit()) {
                    val heightVal: Float = etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightVal: Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightVal / (heightVal * heightVal)
                    displayBMIResult(bmi)
                }
            }
            else{
                if(validateUsUnits()){
                    val usUnitHeightValueFeet: String =
                        etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch: String =
                        etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue: Float = etUsUnitWeight.text.toString()
                        .toFloat()


                    val heightValue =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12
                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                    displayBMIResult(bmi)
                }
            }
        }
        rgUnits.setOnCheckedChangeListener { radioGroup: RadioGroup, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE
        llDiplayBMIResult.visibility = View.VISIBLE


        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNIT_VIEW
        llMetricUnitsView.visibility = View.VISIBLE
        llUsUnitsView.visibility = View.GONE

        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        tvYourBMI.visibility = View.INVISIBLE
        tvBMIValue.visibility = View.INVISIBLE
        tvBMIType.visibility = View.INVISIBLE
        tvBMIDescription.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNIT_VIEW
        llMetricUnitsView.visibility = View.GONE
        llUsUnitsView.visibility = View.VISIBLE

        etUsUnitWeight.text!!.clear()
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()

        tvYourBMI.visibility = View.INVISIBLE
        tvBMIValue.visibility = View.INVISIBLE
        tvBMIType.visibility = View.INVISIBLE
        tvBMIDescription.visibility = View.INVISIBLE
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        if (etUsUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (etUsUnitHeightFeet.text.toString().isEmpty()) {
            isValid = false
        } else if (etUsUnitHeightInch.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateMetricUnit(): Boolean {
        var isValid = true
        if (etMetricUnitHeight.text.toString().isEmpty())
            isValid = false
        else if (etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

}