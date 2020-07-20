package sde.com.kotlinmultipleapps

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

open class AppCompatActivityExtension() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

}