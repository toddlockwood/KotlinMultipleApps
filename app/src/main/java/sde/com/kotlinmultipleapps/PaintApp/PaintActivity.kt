package sde.com.kotlinmultipleapps.PaintApp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_paint.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import sde.com.kotlinmultipleapps.AppCompatActivityExtension
import sde.com.kotlinmultipleapps.R
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class PaintActivity : AppCompatActivityExtension() {

    private var mImageButtonCurrentPaint :ImageButton? = null

    companion object{
        private const val STORAGE_PERMISSION_CODE = 1
        private const val GALLERY = 100

    }

    private inner class BitmapAsyncTask(val mBitmap:Bitmap):AsyncTask<Any,Void,String>(){

        private lateinit var mProgressDialog: Dialog

        override fun onPreExecute() {
            super.onPreExecute()
            showProgressDialog()
        }
        override fun doInBackground(vararg p0: Any?): String {
            var result = ""

            if(mBitmap!= null){
                try {
                    val bytes = ByteArrayOutputStream()
                    mBitmap.compress(Bitmap.CompressFormat.PNG,90,bytes)
                    val f = File(externalCacheDir!!.absoluteFile.toString()
                            + File.separator + "DrawingApp_"
                            + System.currentTimeMillis()/1000
                            + ".png")
                    val fo = FileOutputStream(f)
                    fo.write(bytes.toByteArray())
                    fo.close()
                    result = f.absolutePath
                }catch (e: Exception){
                    result = ""
                    e.printStackTrace()
                }
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            cancelProgressDialog()
            if (!result!!.isEmpty()) {
                Toast.makeText(
                    this@PaintActivity,
                    "File saved successfully :$result",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@PaintActivity,
                    "Something went wrong while saving the file.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            MediaScannerConnection.scanFile(this@PaintActivity, arrayOf(result), null){
                path,uri->val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                shareIntent.type = "image/png"
                startActivity(Intent.createChooser(shareIntent,"Share"))
            }
        }

        private fun showProgressDialog(){
            mProgressDialog = Dialog(this@PaintActivity)
            mProgressDialog.setContentView(R.layout.dialog_custom_progress)
            mProgressDialog.show()
        }

        private fun cancelProgressDialog(){
            mProgressDialog.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)

        mImageButtonCurrentPaint = ll_pallet[1] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pallet_pressed))

        ib_brush.setOnClickListener{
            chooseBrushSizeDialog()
        }
        ib_gallery.setOnClickListener{
            if(isReadStorageAvailable()){
                val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhotoIntent, GALLERY)
            }else{
                reqStoragePermission()
            }
        }
        ib_undo.setOnClickListener{
            drawing_view.onClickUndo()
        }
        ib_save.setOnClickListener{
            if (isReadStorageAvailable()){
                BitmapAsyncTask(drawing_view.getBitmapFromView(frame_layout)).execute()
            }
            else{
                reqStoragePermission()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==Activity.RESULT_OK){
            if (requestCode == GALLERY){
                try {
                    if (data!!.data != null){
                        iv_background.visibility = View.VISIBLE
                        iv_background.setImageURI(data.data)
                    }
                    else{
                        Toast.makeText(this,"Image Error",Toast.LENGTH_LONG).show()
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    fun paintClicked(view: View){
        if(view != mImageButtonCurrentPaint) {
            val imageButton = view as ImageButton
            val colorTag = imageButton.tag.toString()
            drawing_view.setColor(colorTag)
            imageButton!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pallet_pressed))
            mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.pallet_normal))
            mImageButtonCurrentPaint = view
        }
    }

    private fun chooseBrushSizeDialog(){
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size")

        brushDialog.ib_small_brush.setOnClickListener{
            drawing_view.setBrushSize(5F)
            brushDialog.dismiss()
        }
        brushDialog.ib_medium_brush.setOnClickListener{
            drawing_view.setBrushSize(10F)
            brushDialog.dismiss()
        }
        brushDialog.ib_large_brush.setOnClickListener{
            drawing_view.setBrushSize(15F)
            brushDialog.dismiss()
        }
        brushDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Permission Declined",Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun isReadStorageAvailable():Boolean{
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun reqStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE).toString())){
            Toast.makeText(this,"Need Permission",Toast.LENGTH_LONG).show()
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
    }
}