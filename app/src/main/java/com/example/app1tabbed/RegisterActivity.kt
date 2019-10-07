package com.example.app1tabbed

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        textView_register.setOnClickListener {

            val newIntent =  Intent(this, LoginActivity::class.java)
            this.startActivity(newIntent)
            finish()

        }

        button_register.setOnClickListener {

            val username = usuario_edittext_register.text.toString()
            val email = email_edittext_register.text.toString()
            val password = password_edittext_register.text.toString()

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingresa tus datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val httpConnection = HttpConnection()

            val json = JSONObject()
            val urlRegister = "https://neki-platform.herokuapp.com/mobileRegister"

            json.put("username", username)
            json.put("email",email)
            json.put("password",password)

            httpConnection.register(this, this, urlRegister, json)
            //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

        }
    }
}
