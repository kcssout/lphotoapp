package com.example.myphotoapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myphotoapp.retrofit.Login
import com.example.myphotoapp.retrofit.LoginService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity(){
    var TAG : String = LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_login)

        var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.186:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        var loginService : LoginService = retrofit.create(LoginService::class.java)

        var id = edt_id.text
        var pw = edt_pw.text
        btn_login.setOnClickListener {
            loginService.requestLogin(id.toString(),pw.toString()).enqueue(object:Callback<Login>{
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Log.d(TAG, t.toString())

                }
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    Log.d(TAG, "success : "+response.body().toString())
                }
            })

            var dialog = AlertDialog.Builder(this@LoginActivity)
            dialog.setTitle("알림")
            dialog.setMessage("id : "+ id+ "\n ph : "+ pw)
            dialog.show()

        }

    }
}