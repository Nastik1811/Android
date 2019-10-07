package com.example.myapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import android.provider.Settings.Secure
import android.widget.TextView
import android.R.attr.versionName
import android.R.attr.versionCode
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi


class MainActivity : AppCompatActivity() {

    private val STATE_PERMISSION_CODE = 1;

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            requestStatePermission();
        }

        val pinfo = packageManager.getPackageInfo(packageName, 0)
        val telephonyManager: TelephonyManager
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager;
        val verNameTextView = findViewById<TextView>(R.id.verNameView)
        val deviceIdTextView = findViewById<TextView>(R.id.deviceIdView)

        verNameTextView.text = "The app version name is " + pinfo.versionName
        deviceIdTextView.text = telephonyManager.imei


    }

    private fun requestStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)
        ) {

            AlertDialog.Builder(this)
                .setTitle("The app need an access to your phone state")
                .setMessage("This permission is required to display your device ID")
                .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.READ_PHONE_STATE), STATE_PERMISSION_CODE
                    )
                })
                .setNegativeButton("cancel",
                    DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                .create().show()

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE), STATE_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == STATE_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
