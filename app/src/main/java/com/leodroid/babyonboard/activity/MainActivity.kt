package com.leodroid.babyonboard.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.leodroid.babyonboard.R
import com.leodroid.babyonboard.fragment.BabyOnBoardingFragment
import com.leodroid.babyonboard.fragment.FingerCrossedFragment
import com.leodroid.babyonboard.fragment.InvitationFragment
import com.leodroid.babyonboard.services.MultiMediaBGService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isBGPlaying = false
    private var isPlayDefault = false
    private var service: Intent? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_invitation -> {
                loadFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_baby_on_boarding -> {
                loadFragment(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_finger_crossed -> {
                loadFragment(3)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    private fun loadFragment(position: Int) {

        var newFragment: Fragment? = null

        when (position) {
            1 -> newFragment = InvitationFragment()
            2 -> newFragment = BabyOnBoardingFragment()
            3 -> newFragment = FingerCrossedFragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.content, newFragment!!).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        loadFragment(1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actions_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
                //val strShare = String.format(resources.getString(R.string.str_share), getString(R.string.bridegroom), getString(R.string.bride), getString(R.string.app_download_url))
                //i.putExtra(Intent.EXTRA_TEXT, strShare)
                startActivity(Intent.createChooser(i, "Share with"))
            } catch (e: Exception) {
                //e.toString();
            }

            R.id.action_volume_up -> if (isBGPlaying && isPlayDefault) {
                item.setIcon(R.drawable.ic_volume_off_white_48dp)
                item.title = "Volume Off"
                val service = Intent(this@MainActivity, MultiMediaBGService::class.java)
                stopService(service)
                isBGPlaying = false
                isPlayDefault = false
            } else if (!isBGPlaying && !isPlayDefault) {
                item.setIcon(R.drawable.ic_volume_up_white_48dp)
                item.title = "Volume Up"
                val service = Intent(this@MainActivity, MultiMediaBGService::class.java)
                startService(service)
                isBGPlaying = true
                isPlayDefault = false
            } else {
                item.setIcon(R.drawable.ic_volume_off_white_48dp)
                item.title = "Volume Off"
                val service = Intent(this@MainActivity, MultiMediaBGService::class.java)
                stopService(service)
                isBGPlaying = false
            }
            else -> {
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        if (isBGPlaying) {
            service = Intent(this@MainActivity, MultiMediaBGService::class.java)
            stopService(service)
            invalidateOptionsMenu()
        } else {
            service = Intent(this@MainActivity, MultiMediaBGService::class.java)
            startService(service)
            isBGPlaying = true
            isPlayDefault = true
            invalidateOptionsMenu()
        }
    }

    override fun onPause() {
        super.onPause()
        stopService(service)
        isBGPlaying = false
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(service)
        isBGPlaying = false
    }
}
