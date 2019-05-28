package com.example.watbin

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper
import com.ibm.watson.developer_cloud.service.security.IamOptions
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions
import java.util.*

class CameraActivity : AppCompatActivity() {

    var visualRecognition: VisualRecognition? = null
    var cameraHelper: CameraHelper? = null
    var imageView: ImageView? = null
    var detectedObjects: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)
        var SERVER_API_ENDPOINT = "https://watson-waste-sorter-generous-kookaburra.mybluemix.net/api/sort"

        val options = IamOptions.Builder()
            .apiKey(getString(R.string.api_key))
            .build()

        visualRecognition = VisualRecognition("2018_03_19", options)

        cameraHelper = CameraHelper(this)

        imageView = findViewById(R.id.preview)

        detectedObjects = findViewById(R.id.detected_objects)

            cameraHelper?.dispatchTakePictureIntent()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
            val photo = cameraHelper?.getBitmap(resultCode)
            val photoFile = cameraHelper?.getFile(resultCode)
            imageView?.setImageBitmap(photo)

            val classifyOptions = ClassifyOptions.Builder()
                .imagesFile(photoFile)
                .build()

            AsyncTask.execute {
                val response = visualRecognition?.classify(
                    classifyOptions
                )?.execute()

                val classification = response!!.getImages()[0]

                val classifier = classification.classifiers[0]

                val output = StringBuffer()
                for (`object` in classifier.classes) {
                    if (`object`.score > 0.7f)
                        output.append("<")
                            .append(`object`.className)
                            .append("> ")
                }

                runOnUiThread(Runnable {
                    detectedObjects?.setText(output)
                })
            }

        }
    }
}
