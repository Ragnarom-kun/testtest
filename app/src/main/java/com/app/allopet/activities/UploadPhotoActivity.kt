package com.app.allopet

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class UploadPhotoActivity : Activity() {
    private lateinit var _bg__upload_photo_ek2: View
    private lateinit var rectangle: ImageView
    private lateinit var combined_shape: ImageView
    private lateinit var rectangle_ek1: ImageView
    private lateinit var wifi: ImageView
    private lateinit var mobile_signal: ImageView
    private lateinit var _9_41: ImageView
    private lateinit var notch: ImageView
    private lateinit var vector: ImageView
    private lateinit var allopet: TextView
    private lateinit var rectangle_4204: View
    private lateinit var vector_8: ImageView
    private lateinit var rectangle_4205: View
    private lateinit var rectangle_4213: ImageView
    private lateinit var or: TextView
    private lateinit var rectangle_4: View
    private lateinit var rectangle_140: View
    private lateinit var vector_ek1: ImageView
    private lateinit var vector_ek2: ImageView
    private lateinit var vector_ek3: ImageView
    private lateinit var rectangle_141: View
    private lateinit var vector_ek4: ImageView
    private lateinit var vector_ek5: ImageView

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private const val REQUEST_IMAGE_CAPTURE = 2
        private const val REQUEST_IMAGE_PICK = 3
        private const val API_ENDPOINT = "https://your-api-endpoint.com/upload"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_photo)

        _bg__upload_photo_ek2 = findViewById<View>(R.id._bg__upload_photo_ek2)
        rectangle = findViewById<ImageView>(R.id.rectangle)
        combined_shape = findViewById<ImageView>(R.id.combined_shape)
        rectangle_ek1 = findViewById<ImageView>(R.id.rectangle_ek1)
        wifi = findViewById<ImageView>(R.id.wifi)
        mobile_signal = findViewById<ImageView>(R.id.mobile_signal)
        _9_41 = findViewById<ImageView>(R.id._9_41)
        notch = findViewById<ImageView>(R.id.notch)
        vector = findViewById<ImageView>(R.id.vector)
        allopet = findViewById<TextView>(R.id.allopet)
        rectangle_4204 = findViewById<View>(R.id.rectangle_4204)
        vector_8 = findViewById<ImageView>(R.id.vector_8)
        rectangle_4205 = findViewById<View>(R.id.rectangle_4205)
        rectangle_4213 = findViewById<ImageView>(R.id.rectangle_4213)
        or = findViewById<TextView>(R.id.or)
        rectangle_4 = findViewById<View>(R.id.rectangle_4)
        rectangle_140 = findViewById<View>(R.id.rectangle_140)
        vector_ek1 = findViewById<ImageView>(R.id.vector_ek1)
        vector_ek2 = findViewById<ImageView>(R.id.vector_ek2)
        vector_ek3 = findViewById<ImageView>(R.id.vector_ek3)
        rectangle_141 = findViewById<View>(R.id.rectangle_141)
        vector_ek4 = findViewById<ImageView>(R.id.vector_ek4)
        vector_ek5 = findViewById<ImageView>(R.id.vector_ek5)

        // Request camera and storage permissions
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_CAMERA_PERMISSION
            )
        }

        // Set click listeners for camera and gallery buttons
        rectangle_4204.setOnClickListener {
            dispatchTakePictureIntent()
        }

        rectangle_4205.setOnClickListener {
            dispatchPickImageIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dispatchPickImageIntent() {
        val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImageIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    imageBitmap?.let {
                        // Post the captured image bitmap to the API endpoint
                        postImageToEndpoint(it)
                    }
                }
                REQUEST_IMAGE_PICK -> {
                    val selectedImageUri = data?.data
                    selectedImageUri?.let {
                        // Convert the selected image URI to bitmap and post it to the API endpoint
                        val selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                        postImageToEndpoint(selectedBitmap)
                    }
                }
            }
        }
    }

    private fun postImageToEndpoint(bitmap: Bitmap) {
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteStream)
        val byteArray = byteStream.toByteArray()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", "image.jpg", RequestBody.create(MediaType.parse("image/jpeg"), byteArray))
            .build()

        val request = Request.Builder()
            .url(API_ENDPOINT)
            .post(requestBody)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // Handle the API response
                val responseBody = response.body?.string()
                // ...
            }

            override fun onFailure(call: Call, e: IOException) {
                // Handle API call failure
                e.printStackTrace()
            }
        })
    }
}
