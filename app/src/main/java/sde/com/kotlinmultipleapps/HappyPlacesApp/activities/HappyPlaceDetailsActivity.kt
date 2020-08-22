package sde.com.kotlinmultipleapps.HappyPlacesApp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_happy_place_details.*
import sde.com.kotlinmultipleapps.HappyPlacesApp.MapActivity
import sde.com.kotlinmultipleapps.HappyPlacesApp.activities.HappyPlacesApp.Companion.EXTRA_PLACE_DETAILS
import sde.com.kotlinmultipleapps.HappyPlacesApp.models.HappyPlaceModel
import sde.com.kotlinmultipleapps.MainActivity
import sde.com.kotlinmultipleapps.R

class HappyPlaceDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_place_details)

        var happyPlaceDetailModel : HappyPlaceModel? = null
        if (intent.hasExtra(HappyPlacesApp.EXTRA_PLACE_DETAILS)){
            happyPlaceDetailModel = intent.getSerializableExtra(HappyPlacesApp.EXTRA_PLACE_DETAILS) as HappyPlaceModel
        }
        if (happyPlaceDetailModel != null) {

            setSupportActionBar(toolbar_happy_place_detail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = happyPlaceDetailModel.title

            toolbar_happy_place_detail.setNavigationOnClickListener {
                onBackPressed()
            }

            iv_place_image.setImageURI(Uri.parse(happyPlaceDetailModel.image))
            tv_description.text = happyPlaceDetailModel.description
            tv_location.text = happyPlaceDetailModel.location
        }

        btn_view_on_map.setOnClickListener {
            val intent = Intent(this@HappyPlaceDetailsActivity, MapActivity::class.java)
            intent.putExtra(HappyPlacesApp.EXTRA_PLACE_DETAILS, happyPlaceDetailModel)
            startActivity(intent)
        }
    }
    }

