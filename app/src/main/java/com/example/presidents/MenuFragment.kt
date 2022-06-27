package com.example.presidents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton


class MenuFragment : Fragment() {


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
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val btMoney = view.findViewById<ImageButton>(R.id.bt_money)
        val btSign = view.findViewById<ImageButton>(R.id.bt_sign)


        btMoney.setOnClickListener {
            showFragment(CapturePhotoMoneyFragment())
        }

        btSign.setOnClickListener {
            showFragment(CapturePhotoFragment())
        }


        return view;
    }

    private fun showFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}