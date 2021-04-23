package com.example.myphotoapp

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_login)

        var id = edt_id.text
        var pw = edt_pw.text
        btn_login.setOnClickListener { 
            var dialog = AlertDialog.Builder(this@LoginActivity)
            dialog.setTitle("알림")
            dialog.setMessage("id : "+ id)
            dialog.show()
        }
    }
    
}