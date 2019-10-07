package com.example.app1tabbed

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.example.app1tabbed.TabFragments.ContactsFragment
import com.example.app1tabbed.TabFragments.PublicationsFragment


class MainActivity : AppCompatActivity() {

    private var viewPager: ViewPager? = null
    private var tabLayout: TabLayout? = null



    //private var pubFragment: PublicationsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)


        val prefs = getSharedPreferences("storage", Context.MODE_PRIVATE)
        val credentials = prefs.getString("session_token", null)


        if(credentials == null) {
            val newIntent = Intent(this, LoginActivity::class.java)
            this.startActivity(newIntent)
            finish()
        } else {

            val pagerAdapter = PagerAdapter(supportFragmentManager)

            val pubFragment = PublicationsFragment()
            val contactsFragment = ContactsFragment()

            pagerAdapter.addFragment(pubFragment, "Conversación")
            pagerAdapter.addFragment(contactsFragment, "Configuración")

            viewPager!!.adapter = pagerAdapter
            tabLayout!!.setupWithViewPager(viewPager)

            /*val imageView = findViewById<ImageView>(R.id.iv)
            Glide.with(this)
                .load("https://cdn3.iconfinder.com/data/icons/vector-icons-6/96/256-512.png")
                .into(imageView)*/


        }

    }

    class PagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

        private val mFragmentList: ArrayList<Fragment> = ArrayList()
        private val mFragmentTitleList: ArrayList<String> = ArrayList()

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getItem(pos: Int): Fragment? {

            return mFragmentList[pos]

        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

    }

}
