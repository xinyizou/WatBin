//package com.example.watbin
//
//import android.content.Intent
//import android.media.Image
//import android.os.AsyncTask
//import android.os.Build
//import android.os.Bundle
//import android.support.design.widget.FloatingActionButton
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper
//import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition
//import com.ibm.watson.developer_cloud.service.security.IamOptions
//import android.os.AsyncTask.execute
//import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions
//import android.widget.TextView
//import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
//
//
//
//
//class CameraFragment : Fragment() {
//
//    var visualRecognition: VisualRecognition? = null
//    var cameraHelper: CameraHelper? = null
//    var imageView: ImageView? = null
//    var detectedObjects: TextView? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        super.onCreateView(inflater, container, savedInstanceState)
//        val view = inflater.inflate(R.layout.camera, container, false)
//        var SERVER_API_ENDPOINT = "https://watson-waste-sorter-generous-kookaburra.mybluemix.net/api/sort"
//
//        val options = IamOptions.Builder()
//            .apiKey(getString(R.string.api_key))
//            .build()
//
//        visualRecognition = VisualRecognition("2018_03_19", options)
//
//        cameraHelper = CameraHelper(activity)
//
//        imageView = view.findViewById(R.id.preview)
//
//        detectedObjects = view.findViewById(R.id.detected_objects)
//
//        val button = view.findViewById<FloatingActionButton>(R.id.take_picture)
//
//        button.setOnClickListener {
//            cameraHelper?.dispatchTakePictureIntent()
//        }
//
//        return view
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
//            val photo = cameraHelper?.getBitmap(resultCode)
//            val photoFile = cameraHelper?.getFile(resultCode)
//            imageView?.setImageBitmap(photo)
//
//            val classifyOptions = ClassifyOptions.Builder()
//                .imagesFile(photoFile)
//                .build()
//
//            AsyncTask.execute {
//                val response = visualRecognition?.classify(
//                    classifyOptions
//                )?.execute()
//
//                val classification = response!!.getImages()[0]
//
//                val classifier = classification.classifiers[0]
//
//                val output = StringBuffer()
//                for (object in classifier.classes) {
//                    if (`object`.score > 0.7f)
//                        output.append("<")
//                            .append(`object`.getName())
//                            .append("> ")
//                }
//
//                activity?.runOnUiThread(Runnable {
//                    detectedObjects?.setText(output)
//                })
//            }
//
//        }
//    }
//}
//
//
////class CameraFragment : Fragment() {
////
////    var imageView: ImageView? = null
////    var cameraKitView: CameraKitView? = null
////    var SERVER_API_ENDPOINT = "https://watson-waste-sorter-generous-kookaburra.mybluemix.net/api/sort"
////    var visualRecognition: VisualRecognition = VisualRecognition("{version}")
////    var cameraHelper: CameraHelper? = null
////    var photoFile: File? = null
////
////    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
////        super.onCreateView(inflater, container, savedInstanceState)
////
////        val view = inflater.inflate(R.layout.camera, container, false)
////
////        cameraKitView = view.findViewById(R.id.camera)
////
////        cameraHelper = CameraHelper(activity)
////
////        val cameraHelp = cameraHelper
////
////        auth()
////
////        val captureButton = view.findViewById<FloatingActionButton>(R.id.take_picture)
//////        captureButton.setOnClickListener(onClickListener)
////        captureButton.setOnClickListener {
////            cameraHelp?.dispatchTakePictureIntent()
////        }
////
////        return view
////    }
////
////    fun auth() {
////        val options = IamOptions.Builder()
////            .apiKey(SERVER_API_ENDPOINT)
////            .build()
////        visualRecognition = VisualRecognition("2018-03-19", options);
////    }
////
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
////        super.onActivityResult(requestCode, resultCode, data)
////
////        if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
////            val photo = cameraHelper?.getBitmap(resultCode)
////            photoFile = cameraHelper?.getFile(resultCode)
////            imageView?.setImageBitmap(photo)
////
////            backgroundThread()
////        }
////    }
////
////    fun backgroundThread() {
////
////        AsyncTask.execute {
////            var imagesStream: InputStream? = null
////            try {
////                imagesStream = FileInputStream(photoFile)
////            } catch (e: FileNotFoundException) {
////                e.printStackTrace()
////            }
////
////            val classifyOptions = ClassifyOptions.Builder()
////                .imagesFile(imagesStream)
////                .imagesFilename(photoFile?.getName())
////                .threshold(0.6.toFloat())
////                .owners(Arrays.asList("me"))
////                .build()
////            val result = visualRecognition.classify(classifyOptions).execute()
////            val gson = Gson()
////            val json = gson.toJson(result)
////            var name: String? = null
////            try {
////                val jsonObject = JSONObject(json)
////                val jsonArray = jsonObject.getJSONArray("images")
////                val jsonObject1 = jsonArray.getJSONObject(0)
////                val jsonArray1 = jsonObject1.getJSONArray("classifiers")
////                val jsonObject2 = jsonArray1.getJSONObject(0)
////                val jsonArray2 = jsonObject2.getJSONArray("classes")
////                val jsonObject3 = jsonArray2.getJSONObject(0)
////                name = jsonObject3.getString("class")
////            } catch (e: JSONException) {
////                e.printStackTrace()
////            }
////
////                val finalName = name
////                runOnUiThread(Runnable {
////                    val detectedObjects = findViewById(R.id.text_view_main)
////                    detectedObjects.setText(finalName)
////                })
////        }
////    }
////}
//
////    override fun onStart() {
////        super.onStart()
////        cameraKitView?.onStart()
////    }
////
////    override fun onResume() {
////        super.onResume()
////        cameraKitView?.onResume()
////    }
////
////    override fun onPause() {
////        cameraKitView?.onPause()
////        super.onPause()
////    }
////
////    override fun onStop() {
////        cameraKitView?.onStop()
////        super.onStop()
////    }
////
////    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        cameraKitView?.onRequestPermissionsResult(requestCode, permissions, grantResults)
////    }
////
////    private val onClickListener = View.OnClickListener {
////        cameraKitView!!.captureImage(CameraKitView.ImageCallback { cameraKitView, photo ->
////            val savedPhoto = File(Environment.getExternalStorageDirectory(), "photo.jpg")
////            try {
////                val outputStream = FileOutputStream(savedPhoto.getPath())
////                outputStream.write(photo)
////                outputStream.close()
////            } catch (e: java.io.IOException) {
////                e.printStackTrace()
////                Log.e("CKDemo", "Exception in photo callback")
////            }
////        })
////
////    }
////
////    fun parseReturn() {
////        showAlert("Hi", 100)
////    }
////
////    fun showAlert(text: String, confidence: Int) {
////        var res = text
////        if (res == "Image is either not a waste or it's too blurry, please try it again.") {
////            res = "Unclassified"
////        }
////
////        AlertDialog.Builder(context)
////            .setTitle(res)
////            .setMessage("Confidence: " + confidence)
////            .setNegativeButton(android.R.string.ok, null)
////            .show()
////    }
//
////}