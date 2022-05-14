package com.example.presidents

import android.app.Activity
import android.content.Intent
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
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


private const val FILE_NAME = "photo.jpg"
private const val REQUEST_CODE=42
private lateinit var photoFile:File
const val BASE_URL = "http://192.168.1.7:5000/"


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

            Log.d("MainWork", "Photo_URL" + photoFile)
            Log.d("MainWork", "Store is Running")
            storeMyData(photoFile)
        }

        return view
    }

//
//    private fun storeMyData(photo: File) {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .build()
//            .create(ApiInterface::class.java)
//
//        val jsonObject = JSONObject()
//
//        jsonObject.put("image", photo)
//
//        val jsonObjectString = jsonObject.toString()
//
//        val retrofitData = retrofit.storePost(jsonObjectString)
//
//        retrofitData.enqueue(object : Callback<MyData?> {
//            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
//                Log.d("MainWork", "OnResponse Running")
//                val responseBody = response.body()
//                Log.d("MainWork", "OnResponse: "+responseBody)
//            }
//
//            override fun onFailure(call: Call<MyData?>, t: Throwable) {
//                Log.d("MainWork", "onFailure: "+t.message)
//            }
//        })
//
//
//    }

    private fun storeMyData(photo: File) {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val maps :HashMap<String, String> = HashMap()


        Log.d("MainWork", "OnResponse Running1"+photo.name)
        val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)

        maps.put("image", takenImage.toString() )

        Log.d("MainWork", "OnResponse Running"+maps)



        val retrofitData = retrofitBuilder.storePost(maps)

        Log.d("MainWork", "OnResponse Running"+retrofitData)

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                Log.d("MainWork", "OnResponse Running")
                val responseBody = response.body()
                Log.d("MainWork", "OnResponse: "+ responseBody)
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("MainWork", "onFailure: "+t.message)
            }
        })
    }


//    private fun getMyData() {
//        val retrofitBuilder = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(ApiInterface::class.java)
//
//        Log.d("MainWork", "getMyData: 1")
//        val retrofitData = retrofitBuilder.getData()
//        Log.d("MainWork", "getMyData: 2")
//        retrofitData.enqueue(object : Callback<MyData?> {
//            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
//                Log.d("MainWork", "getMyData: 3")
//                val responseBody = response.body()!!
//                Log.d("MainWork", "getMyData: 4"+responseBody)
//
//
//                val txt = view!!.findViewById<TextView>(R.id.presidents)
//                txt.text= responseBody.toString()
//
//
//                Log.d("MainWork", "getMyData: 4")
//
//
//
//            }
//
//            override fun onFailure(call: Call<MyData?>, t: Throwable) {
//                Log.d("Main", "onFailure: "+t.message)
//            }
//        })
//    }

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

            Log.d("MainWork", "imageView: "+ takenImage )

            Log.d("MainWork", "imageView: "+ takenImage as Bitmap )
            Log.d("MainWork", "imageView: "+ imageView)

        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}