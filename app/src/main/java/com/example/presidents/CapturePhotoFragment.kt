package com.example.presidents

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE=42
private lateinit var photoFile:File
class CapturePhotoFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_capture_photo, container, false)

        val bt_take_photo = view.findViewById<Button>(R.id.bt_take_photo)
        bt_take_photo.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            photoFile = getPhotoFile(FILE_NAME)


//            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)
           val fileProvider = FileProvider.getUriForFile(requireActivity(),"com.example.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider)


            if (takePictureIntent.resolveActivity(requireActivity().packageManager)!=null){
                startActivityForResult(takePictureIntent,REQUEST_CODE)
            }else{
                Toast.makeText(activity,"Unable to open Camera",Toast.LENGTH_LONG).show()
            }
        }
        return view;
    }

    private fun getPhotoFile(fileName: String): File {

       val storageDir = getActivity()?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
//           val takenImage = data?.extras?.get("data")

            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)


           val imageView = view?.findViewById<ImageView>(R.id.image)
            imageView?.setImageBitmap(takenImage as Bitmap?)

        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}