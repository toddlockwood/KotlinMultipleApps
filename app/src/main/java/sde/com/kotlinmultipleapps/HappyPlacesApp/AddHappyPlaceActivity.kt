package sde.com.kotlinmultipleapps.HappyPlacesApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_happy_place.*
import sde.com.kotlinmultipleapps.R

class AddHappyPlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_happy_place)
        setSupportActionBar(toolbar_add_place)

        toolbar_add_place.setNavigationOnClickListener {

        }
    }
}