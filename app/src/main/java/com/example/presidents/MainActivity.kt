package com.example.presidents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(SplashFragment())
        mTimerTask = object : TimerTask() {
            override fun run() {
                showFragment(CapturePhotoFragment())
            }
        }
        mTimer = Timer()
        mTimer!!.schedule(mTimerTask, 3000)
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}