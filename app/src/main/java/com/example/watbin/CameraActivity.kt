package com.example.watbin

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.ibm.watson.developer_cloud.service.security.IamOptions
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions
import kotlinx.android.synthetic.main.camera.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.util.*


class CameraActivity : AppCompatActivity() {

    var mVisualRecognition: VisualRecognition? = null
    private val PERMISSION_CODE: Int = 1000
    private val IMAGE_CAPTURE_CODE: Int = 1001
    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)

        take_picture.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    openCamera()
                }
            }
            else {
                openCamera()
            }
        }


        val options = IamOptions.Builder()
            .apiKey("b1c95QXHSJTXWG5h26YuKq8kdz7xd4qzfjDRvwDx_UH-")
            .build()
        mVisualRecognition = VisualRecognition("2018-03-19", options)

    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            preview.setImageURI(image_uri)
            val photo: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, image_uri)
            detected.text = "Loading ..."

//            val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            var networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
//            val isWifiConnected = networkInfo.isConnected
//            networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
//            val isMobileConnected = networkInfo.isConnected

            val photoFile = File.createTempFile("SHARE_", ".jpg", cacheDir)
            val output = FileOutputStream(photoFile)
            photo.compress(Bitmap.CompressFormat.JPEG, 100, output)
            output.flush()
            output.close()

            AsyncTask.execute(object: Runnable {

                override fun run() {
                    var imagesStream: InputStream? = null
                    try {
                        imagesStream = FileInputStream(photoFile)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace();
                    }
                    var classifyOptions: ClassifyOptions = ClassifyOptions.Builder()
                        .imagesFile(imagesStream)
                        .imagesFilename(photoFile.getName())
                        .threshold(0.6F)
                        .classifierIds(Arrays.asList("DefaultCustomModel_630285397"))
                        .build();
                    var result: ClassifiedImages = mVisualRecognition!!.classify(classifyOptions).execute()
                    System.out.println(result);

                    var gson: Gson = Gson();
                    var json: String = gson.toJson(result);
                    var name: String? = null;
                    var score: String? = null
                    try {
                        var jsonObject: JSONObject = JSONObject(json);
                        var jsonArray: JSONArray = jsonObject.getJSONArray("images");
                        var jsonObject1: JSONObject = jsonArray.getJSONObject(0);
                        var jsonArray1: JSONArray = jsonObject1.getJSONArray("classifiers");
                        var jsonObject2: JSONObject= jsonArray1.getJSONObject(0);
                        var jsonArray2: JSONArray = jsonObject2.getJSONArray("classes");
                        var jsonObject3: JSONObject = jsonArray2.getJSONObject(0);
                        name = jsonObject3.getString("class");
                        score = jsonObject3.getString("score")
                    } catch (e: JSONException) {
                        e.printStackTrace();
                    }
                    val finalName: String = "You should " + name!! + "! \nWe have " + score!! + " confidence"
                    runOnUiThread( object: Runnable {
                        override fun run() {
                            val detectedObjects = findViewById<TextView>(R.id.detected);
                            detectedObjects.setText(finalName);
                        }
                    });
                }
            });
        }
    }

}
