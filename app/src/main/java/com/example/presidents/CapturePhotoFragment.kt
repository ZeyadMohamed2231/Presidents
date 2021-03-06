package com.example.presidents

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

private const val FILE_NAME = "photo"
private const val REQUEST_CODE=42
private lateinit var photoFile:File
const val BASE_URL = "http://192.168.1.3:5000/"




class CapturePhotoFragment : Fragment() {
    var count = true
    lateinit var mainText: String

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
        val view = inflater.inflate(R.layout.fragment_capture_photo, container, false) //Calling Fragment Page which is needed

        val btTakePhoto = view.findViewById<Button>(R.id.bt_take_photo)
        btTakePhoto.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)
           val fileProvider = FileProvider.getUriForFile(requireActivity(),"com.example.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileProvider)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager)!=null){
                startActivityForResult(takePictureIntent,REQUEST_CODE)
            }else{
                Toast.makeText(activity,"Unable to open Camera",Toast.LENGTH_LONG).show()
            }
        }




        val btPredict = view.findViewById<Button>(R.id.Predict)
        btPredict.setOnClickListener {
            storeMyData(photoFile)
        }

        return view
    }






    private fun storeMyData(file: File) {

        val retrofitBuilder = Retrofit.Builder()     //create my APIS
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()



        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)    //Take image with class multipart

        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)      // key


        val apiService: ApiInterface = retrofitBuilder.create(ApiInterface::class.java)     // Build

        val retrofitData: Call<MyData> = apiService.storePost(body)     // Calling APis




        retrofitData.enqueue(object : Callback<MyData?> {      // Check if Apis is working or not working: Put Predict in text if not display toast server error
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                if (response.isSuccessful) {
                    val txt = view?.findViewById<TextView>(R.id.tv_predict)
                    val text = response.body().toString()
                    var substring = text.subSequence(18, text.length-1).toString()

                    if(count){
                        count= false
                        txt?.text = substring
                        mainText = substring
                    }else if (!count){

                        mainText = mainText.plus(substring)
                        txt?.text = mainText
                    }


                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Toast.makeText(activity,"SERVER_ERROR (Try Again)",Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun getPhotoFile(fileName: String): File {            // Get Photo from  dict of android
       val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {      // Check in application witch camera if photo is okay so decode it with bitmap or not
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){


            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)

           val imageView = view?.findViewById<ImageView>(R.id.image)
            imageView?.setImageBitmap(takenImage)



        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}