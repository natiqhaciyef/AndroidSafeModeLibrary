package com.natiqhaciyef.android_safe_mode_util.managers

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import com.natiqhaciyef.android_safe_mode_util.models.enums.FileTypes

object IntentManager {
    private const val INTENT_MANAGER_LOG = "Intent Manager Log => "

    fun openUri(context: Context, uri: Uri){
        try {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(INTENT_MANAGER_LOG, "Exception: ${e.localizedMessage}")
        }
    }

    fun openGoogleSearch(context: Context, searchText: String) {
        val uri = Uri.Builder()
            .scheme("https")
            .authority("www.google.com")
            .path("search")
            .appendQueryParameter("q", searchText)
            .build()

        try {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(INTENT_MANAGER_LOG, "Exception: ${e.localizedMessage}")
        }
    }


    fun Activity.openOtherApp(
        description: String = "This is my text to send.",
        destinationPackage: String
    ) {
        try {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, description)
//                putExtra("jid", "${data.phone}@s.whatsapp.net")
                type = FileTypes.getIntentFileType(FileTypes.URL)
                setPackage(destinationPackage)
            }
            startActivity(sendIntent)
        } catch (e: Exception) {
            Log.e(INTENT_MANAGER_LOG, e.message ?: e.localizedMessage)
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$destinationPackage")
                    )
                )
            } catch (e: android.content.ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$destinationPackage")
                    )
                )
            }
        }
    }

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

                if (capabilities != null){
                    return when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
        }

        return false
    }


    private fun Activity.openPlayMarketForRateApp(packageName: String) {
        val uri = Uri.parse("market://details?id=$packageName");
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            println(e.localizedMessage)
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}