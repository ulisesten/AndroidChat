package com.example.app1tabbed

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //val loginToolbar = findViewById<Toolbar>(R.id.toolbar_login)
        //setSupportActionBar(loginToolbar)

        send_button_login.setOnClickListener {

            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa tus datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val httpConnection = HttpConnection()

            val urlLogin = "https://neki-platform.herokuapp.com/mobileLogin"
            val json = JSONObject()

            json.put("email",email)
            json.put("password",password)

            httpConnection.login(this, this, urlLogin, json)

        }

        textView_login.setOnClickListener {
            Log.d("LoginActivity","to RegisterActivity")
            val newIntent = Intent(this, RegisterActivity::class.java)
            this.startActivity(newIntent)
            finish()
        }
    }
}
