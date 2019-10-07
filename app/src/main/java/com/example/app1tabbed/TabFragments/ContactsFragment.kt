package com.example.app1tabbed.TabFragments


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.app1tabbed.LoginActivity

import com.example.app1tabbed.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ContactsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_contacts, container, false)

        val closeSession = rootView.findViewById(R.id.close_session) as TextView



        closeSession.setOnClickListener {

            Toast.makeText(activity, "Cerrando sesión", Toast.LENGTH_SHORT).show()
            val prefs = activity!!.getSharedPreferences("storage", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()

            val newIntent = Intent(activity, LoginActivity::class.java)
            this.startActivity(newIntent)
            activity!!.finish()

        }

        return rootView
    }


}
