package com.example.presidents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.widget.Button
import android.widget.Toast


class CapturePhotoMoneyFragment : Fragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_capture_photo_money, container, false)

        val btTakePhoto = view.findViewById<Button>(R.id.bt_take_photo_CD)
        val btPredict = view.findViewById<Button>(R.id.bt_Predict_CD)

        btTakePhoto.setOnClickListener {
//            var mediaPlayer = MediaPlayer.create(context, R.raw.m50)
//            mediaPlayer.start() // no need to call prepare(); create() does that for you

//            var i = "ص"
//            var e = 'ع'
//            var g = 'ب'
//            val c = i.plus(e).plus(g)
//            Log.d("Testtt", "onCreateView: "+c)

        }

        btPredict.setOnClickListener {
            var mediaPlayer = MediaPlayer.create(context, R.raw.m100)
            mediaPlayer.start() // no need to call prepare(); create() does that for you
        }

        return view
    }


}