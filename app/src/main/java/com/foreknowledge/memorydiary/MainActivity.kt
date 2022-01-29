package com.foreknowledge.memorydiary

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import com.pedro.library.AutoPermissions
import com.pedro.library.AutoPermissionsListener


class MainActivity : AppCompatActivity() , AutoPermissionsListener {
    private val tag = ListActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mainBtn = findViewById<View>(R.id.mainButton) as Button
        val mainBtn2 = findViewById<View>(R.id.mainButton2) as Button
        val mainBtn4 = findViewById<View>(R.id.mainButton4) as Button
        mainBtn.setOnClickListener { //intent 화면이동
            val intent = Intent(this, CreateMemoActivity::class.java)
            startActivity(intent)
        }
        mainBtn2.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
        mainBtn4.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", DialogInterface.OnClickListener { dialog, whichButton ->
                    val i = Intent(this@MainActivity, LoginActivity::class.java)
                    Toast.makeText(applicationContext, "로그아웃 됐습니다", Toast.LENGTH_LONG).show()
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(i)
                })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, whichButton -> })
                .show()
        }
        AutoPermissions.Companion.loadAllPermissions(this, RequestCode.PERMISSION_REQUEST_CODE)

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this)
    }

    override fun onDenied(requestCode: Int, permissions: Array<String>) {
        Log.d(tag,"permissions denied : ${permissions.size}")
    }

    override fun onGranted(requestCode: Int, permissions: Array<String>) {
        Log.d(tag,"permissions granted : ${permissions.size}")
    }
}