package sde.com.kotlinmultipleapps.HappyPlacesApp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_happy_places_app.*
import sde.com.kotlinmultipleapps.HappyPlacesApp.database.DatabaseHandler
import sde.com.kotlinmultipleapps.HappyPlacesApp.models.HappyPlaceModel
import sde.com.kotlinmultipleapps.HappyPlacesApp.utils.SwipeToDeleteCallback
import sde.com.kotlinmultipleapps.HappyPlacesApp.utils.SwipeToEditCallback
import sde.com.kotlinmultipleapps.MainActivity
import sde.com.kotlinmultipleapps.R
import sde.com.kotlinmultipleapps.adapters.HappyPlacesAdapter

class HappyPlacesApp : AppCompatActivity() {

    companion object{
        var ADD_PLACE_ACTIVITY_REQUEST_CODE = 1
        var EXTRA_PLACE_DETAILS = "extra_place_details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_places_app)

        fabAddHappyPlace.setOnClickListener {
            val intent = Intent(this, AddHappyPlaceActivity::class.java)
            startActivityForResult(intent, ADD_PLACE_ACTIVITY_REQUEST_CODE)
        }

        getHappyPlacesFromLocalDB()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PLACE_ACTIVITY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                getHappyPlacesFromLocalDB()
            }
        }
    }

    private fun setupHappyPlacesRecyclerView(happyPlaceList: ArrayList<HappyPlaceModel>){
        rv_happy_places_list.layoutManager = LinearLayoutManager(this)
        rv_happy_places_list.setHasFixedSize(true)
        val placesAdapter = HappyPlacesAdapter(this,happyPlaceList)
        rv_happy_places_list.adapter = placesAdapter
        placesAdapter.setOnClickListener(object : HappyPlacesAdapter.OnClickListener{
            override fun onClick(position: Int, model: HappyPlaceModel) {
                val intent = Intent(this@HappyPlacesApp, HappyPlaceDetailsActivity::class.java)
                intent.putExtra(EXTRA_PLACE_DETAILS, model)
                startActivity(intent)
            }
        })

        val editSwipeHandler = object : SwipeToEditCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_happy_places_list.adapter as HappyPlacesAdapter
                adapter.notifyEditItem(this@HappyPlacesApp, viewHolder.adapterPosition, ADD_PLACE_ACTIVITY_REQUEST_CODE)
            }
        }

        val deleteSwipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = rv_happy_places_list.adapter as HappyPlacesAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                getHappyPlacesFromLocalDB()
            }
        }

        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(rv_happy_places_list)
        deleteItemTouchHelper.attachToRecyclerView(rv_happy_places_list)
    }

    private fun getHappyPlacesFromLocalDB(){
        val dbHandler = DatabaseHandler(this)
        val happyPlaceList = dbHandler.getHappyPlacesList()

        if(happyPlaceList.size > 0){
            rv_happy_places_list.visibility = View.VISIBLE
            tv_no_records_available.visibility = View.GONE
            setupHappyPlacesRecyclerView(happyPlaceList)
        }
        else{
            rv_happy_places_list.visibility = View.GONE
            tv_no_records_available.visibility = View.VISIBLE
        }
    }
}