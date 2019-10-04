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


class MainActivity : AppCompatActivity() {

    private val STATE_PERMISSION_CODE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            requestStatePermission();
        }
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
