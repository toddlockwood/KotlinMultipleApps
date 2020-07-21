package sde.com.kotlinmultipleapps.PaintApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_paint.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.R

class PaintActivity : AppCompatActivityExtension() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)

        drawing_view.setBrushSize(15F)
    }
}