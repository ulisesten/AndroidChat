package com.example.app1tabbed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


/**ulisesten, julio 1, 2019*/

class HttpConnection {



    fun login(context: Context,
              activity: Activity,
              url: String,
              json: JSONObject){

        Log.d("loginActivity", url)

        val queue = Volley.newRequestQueue(context)

        val jsonObjectRequest = makeRequest(context, activity, url, json)

        queue.add(jsonObjectRequest)

    }



    fun register(context: Context,
                 activity: Activity,
                 url: String,
                 json: JSONObject){

        Log.d("RegisterActivity", url)

        val queue = Volley.newRequestQueue(context)

        val jsonObjectRequest = makeRequest(context, activity, url, json)

        queue.add(jsonObjectRequest)

    }


    private fun makeRequest(context: Context,
                            activity: Activity,
                            url: String,
                            jsonBody: JSONObject
                           ): JsonObjectRequest{

        return JsonObjectRequest( Request.Method.POST, url, jsonBody,
            Response.Listener { response ->



                Log.d("Register", response.getString("credentials"))

                storeCredentials(activity,
                    response.getString("username"),
                    response.getString("credentials"))

                nextActivity(context, activity)

            },
            Response.ErrorListener { error ->

                val networkResponse =  error.networkResponse

                if (networkResponse != null && networkResponse.statusCode != 200 ) {
                    // HTTP Status Code: 401 Unauthorized
                    Log.d("RegisterError", error.toString())
                    Toast.makeText(context, "Error.", Toast.LENGTH_SHORT).show()
                }

            })
    }

    /** Change to the next activity*/
    private fun nextActivity(context: Context,
                             activity: Activity
                            ){

        val newIntent = Intent(context, MainActivity::class.java)
        newIntent.putExtra("isAuth",true)
        context.startActivity(newIntent)
        activity.finish()

    }

    private fun storeCredentials(activity: Activity,
                                 username: String,
                                 token: String
                                ){

        val prefs = activity.getSharedPreferences("storage", Context.MODE_PRIVATE ).edit()

        prefs.putString("username", username)
        prefs.putString("session_token",token)
        prefs.apply()

    }
}