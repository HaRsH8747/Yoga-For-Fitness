package com.myapplication

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapplication.adapter.YogaBenefitsAdapter
import com.myapplication.databinding.ActivityYogaDetailVideoBinding
import com.myapplication.utils.AppPref
import com.myapplication.utils.Utils
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.regex.Pattern

class YogaDetailVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYogaDetailVideoBinding
    private lateinit var appPref: AppPref
    private var isFavourite = false
    private lateinit var progressDialog: Dialog

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaDetailVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_circular)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        appPref = AppPref(this)
        val uri: Uri = Uri.parse(Utils.yogaDetailVideo.details.media)
        binding.videoView.setVideoURI(uri)
        binding.videoView.start()
        binding.videoView.setOnClickListener {
            if (binding.videoView.isPlaying){
                binding.videoView.pause()
            }else{
                binding.videoView.start()
            }
        }

        binding.btnRate.setOnClickListener {
            goToPlayStore()
        }

        binding.share.setOnClickListener {
            progressDialog.show()
            lifecycleScope.launch {
                shareApplication()
            }
        }

        binding.tvYogaName.text = Utils.yogaDetailVideo.details.title
        binding.tvYodaDetail.text = Utils.yogaDetailVideo.details.description

        binding.rvBenefits.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val benefitAdapter = YogaBenefitsAdapter(this,Utils.yogaDetailVideo.details.benefit)
        binding.rvBenefits.adapter = benefitAdapter

        var favourites = appPref.getString(AppPref.FAVOURITE_YOGA)
        var newFavourites = ""
        if (!favourites.isNullOrEmpty()){
            isFavourite = checkForFavourite(favourites)
        }
        if (isFavourite){
            binding.btnFav.text = "Remove From Favourites"
        }else{
            binding.btnFav.text = "Add to Favourites"
        }
        binding.btnFav.setOnClickListener {
            if (isFavourite){
                isFavourite = !isFavourite
                val pattern = Pattern.compile(",")
                for (item in pattern.split(favourites!!)){
                    if (item != Utils.currentYogaId.toString()){
                        newFavourites += "$item,"
                    }
                }
                favourites = newFavourites
                appPref.setString(AppPref.FAVOURITE_YOGA,newFavourites)
                binding.btnFav.text = "Add to Favourites"
            }else{
                isFavourite = !isFavourite
                favourites += "${Utils.currentYogaId},"
                appPref.setString(AppPref.FAVOURITE_YOGA,favourites)
                binding.btnFav.text = "Remove from Favourites"
            }
        }

        binding.arrowBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun goToPlayStore() {
        val uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        try {
            startActivity(goToMarket)
        } catch (e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)))
        }
    }

    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val isDeleted: Boolean = deletePhotoFromInternalStorage()
        } else {
            deletePhotoFromInternalStorage()
        }
    }

    private fun deletePhotoFromInternalStorage(): Boolean {
        return try {
            val file = File(filesDir, "logo.png")
            file.delete()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun shareApplication() {
        try {
            val bmp =
                BitmapFactory.decodeResource(this.resources, R.drawable.logo)
            saveToInternalStorage(bmp)
            val uri: Uri? = loadImageFromStorage()
            grantUriPermission(BuildConfig.APPLICATION_ID,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "image/png"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Jingle Player")
            val shareMessage =
                "\nYoga Fitness App. Get in detail Information about different yoga pose.\n\nhttps://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            resultLauncher.launch(Intent.createChooser(shareIntent, "Choose one"))
            progressDialog.dismiss()
        } catch (e: Exception) {
            progressDialog.dismiss()
            e.printStackTrace()
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap) {
        val myPath = File(filesDir, "logo.png")
        Log.d("CLEAR", "save file: $myPath")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(myPath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            progressDialog.dismiss()
        } finally {
            try {
                fos!!.close()
                progressDialog.dismiss()
            } catch (e: IOException) {
                e.printStackTrace()
                progressDialog.dismiss()
            }
        }
    }

    private fun loadImageFromStorage(): Uri? {
        return try {
            val myPath = File(filesDir, "logo.png")
            Log.d("CLEAR", "load file: $myPath")
            FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".provider",
                myPath)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            progressDialog.dismiss()
            null
        }
    }

    private fun checkForFavourite(favouriteString: String?): Boolean {
        val pattern = Pattern.compile(",")
        val favourites = pattern.split(favouriteString!!)
        for (item in favourites){
            if (item == (Utils.currentYogaId).toString()){
                return true
            }
        }
        return false
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//    }
}