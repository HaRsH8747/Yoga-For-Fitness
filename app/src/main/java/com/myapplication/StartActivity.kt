package com.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.MutableLiveData
import com.airbnb.lottie.LottieAnimationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.myapplication.api.RetrofitInstance
import com.myapplication.models.Category
import com.myapplication.models.YogaList
import com.myapplication.models.YogaListId
import com.myapplication.utils.ConnectionLiveData
import com.myapplication.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartActivity : AppCompatActivity() {

    val token: String = "xaZPmP8Fu9hdbhHasB"
    private lateinit var resultCategory: Call<Category>
    private lateinit var resultYogaList: Call<YogaList>
    private var isFetched = MutableLiveData(false)
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var appUpdateManager: AppUpdateManager
    private val MY_REQUEST_CODE: Int = 99
//    companion object{
//        lateinit var startActivity: StartActivity
//    }
//    const val

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
//        startActivity = this
        addNetworkListener(this)
        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkUpdate()
        val intent = Intent(this, CategoryActivity::class.java)
        isFetched.observe(this){
            if (it){
                startActivity(intent)
                finish()
            }
        }
    }

    private val listener: InstallStateUpdatedListener = InstallStateUpdatedListener { installState ->
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            Log.d("CLEAR", "An update has been downloaded")
            appUpdateManager.completeUpdate()
        }
    }

    private fun checkUpdate() {
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        // Checks that the platform will allow the specified type of update.
        Log.d("CLEAR", "Checking for updates")
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                appUpdateManager.registerListener(listener)
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MY_REQUEST_CODE)
                Log.d("CLEAR", "Update available")
            } else {
                Log.d("CLEAR", "No Update available")
            }
        }
    }

    private fun fetchCategoryData() {
//        val intent = Intent(this, CategoryActivity::class.java)
        val yogaApi = RetrofitInstance.api
        GlobalScope.launch {
            resultCategory = yogaApi.getCategories(token)
            resultCategory.enqueue(object : Callback<Category> {
                override fun onResponse(call: Call<Category>, response: Response<Category>) {
                    if(response.body() != null){
                        Utils.categoryResponse = response.body()!!
                        fetchYogaList()
//                        startActivity(intent)
//                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
//                        finish()
                        Log.d("CLEAR","response: ${Utils.categoryResponse}")
                    }
                }
                override fun onFailure(call: Call<Category>, t: Throwable) {
                    Log.d("CLEAR","MainActivity: ${t.message}")
//                    Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun fetchYogaList(){
        val yogaApi = RetrofitInstance.api
        for (type in Utils.categoryResponse.type){
            GlobalScope.launch {
                resultYogaList = yogaApi.getYogaList(type.id.toString(),token)
                resultYogaList.enqueue(object : Callback<YogaList> {
                    override fun onResponse(call: Call<YogaList>, response: Response<YogaList>) {
                        if(response.body() != null){
                            Utils.allYogaList.add(YogaListId(type.id,response.body()!!.yogalist.toMutableList()))
//                            if (type.title == Utils.BEGINNERS){
//                            }else if (type.title == Utils.INTERMEDIATE){
//                                Utils.intermediateList = response.body()!!.yogalist.toMutableList()
//                            }
//                            Log.d("CLEAR","response:")
                            if (!isFetched.value!!){
                                isFetched.postValue(true)
                            }
    //                        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
//                            finish()
                        }
                    }
                    override fun onFailure(call: Call<YogaList>, t: Throwable) {
                        Log.d("CLEAR","MainActivity: ${t.message}")
                        isFetched.postValue(false)
//                        Toast.makeText(this@StartActivity,"Unable to fetch data!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    private fun addNetworkListener(context: Context){
        val networkDialog = AppCompatDialog(context)
        networkDialog.setContentView(R.layout.check_network_dialog)
        val lottieAnimation = networkDialog.findViewById<LottieAnimationView>(R.id.lvInternet)
        networkDialog.setCancelable(false)
        networkDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lottieAnimation?.playAnimation()
        if (!isOnline(this)){
            networkDialog.show()
        }
        connectionLiveData = ConnectionLiveData(application)
        connectionLiveData.observe(this) { isAvailable ->
            when (isAvailable) {
                true -> {
                    if (networkDialog.isShowing) {
                        networkDialog.dismiss()
//                        AppOpenManager.internetObserver.postValue(true)
                    }
                    fetchCategoryData()
                }
                false -> {
                    networkDialog.show()
                }
            }
        }
    }
}