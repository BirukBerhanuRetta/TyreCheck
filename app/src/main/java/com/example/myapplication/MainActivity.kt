package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream


private const val REQUEST_CODE = 42

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // this has my image that is being preveiwed
         val getPreviewImage=
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode == Activity.RESULT_OK){
                    val takenImage= it.data?.extras?.get("data")  as Bitmap
                    imageView.setImageBitmap(takenImage)
                    val baos = ByteArrayOutputStream()
                    takenImage.compress(Bitmap.CompressFormat.PNG, 100, baos)
                    val b = baos.toByteArray()
                    val s= Base64.encodeToString(b, Base64.DEFAULT)

                    // this is me trying to make it into a JSON object
                    // not really sure if I did it right, but still don't exactly know how to send this JSON to the backend
                   val ans = JSONObject (""" {
                        "instances": [{
                        "content": s }],
                        "parameters": {
                        "confidenceThreshold": 0.5,
                        "maxPredictions": 5
                    }
                    }""")
                    // this was my backend key that I got from the model we trained
                    //We need to replace this as we need a new backend
                    val key = JSONObject("""{
  "type": "service_account",
  "project_id": "mais-364217",
  "private_key_id": "a59a5eaebaec1a913b321b800ff37444cdb6c107",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDMCapgWN34NCBr\nFHpzSBDTynRApHhQ212gJTOzaob3UA6SobhKY855jbdQ9BbOZkeWF+7gjwUZ70nE\nZ2JUjUcLKTVd/A3iWc6/PZPGFzHcAE3Bn7OKFSSO8Z5HyHEiHIJYcP7qPsEN8/PJ\n/npgiyxoQU1ViRnv97xm3ePjrH2WqFQ3JkDF6wgZyCDLJlL95B6461q0PGw1oQ9x\nn6wQuaX8iEtjrrOeDHffo0e40y45P6FIGkz/kSJ9VK/60luE4dgRl170r0Avl9GD\nkxgFuiiMbhdvidjybjTWmU/fhojneQkR1vtrHijjgFnnxJRXlgBuZmfrq4+EUV+7\nNqDdIqABAgMBAAECggEABANyKKJC3n+e/iA5XuRlYVcfhYh+rd98xlpI+6/eHwYB\n/WAlPQBc1r8DArqEatOv94u5uQzTJb0TW0PJ1z4urjFlsXc0ajYIRzJ9wkUhR9Gx\nNdxvdok/Lnkh+wk+MXS0WytA1mh82WqJqnjhHR5LtbLII8U2P4ZE3FSjx44Ua9bT\nkEVOr0RIoOrxYZgVFpqpaA0BM/dwlQ1hELqJzjvim7mfb2P5TW0Lg+sAGWDQvcMF\n/BiMop6IgsX9Ev6PQb65+IqIFhFegpFa7vmBnQHwNh6aDF/uPmlkmD5RspLA699j\nezl3HkHfm/Hw45Ym2I8PyNlwZYwaEwExYKlSs4ejeQKBgQDnuzC3nDpOdPSSqrq4\nUKnCwGvZLV+I4oEpcenRjv45lOQdBnZLNtB049BBWk44Qf/Zu1smTuVpmSVdv8Vd\nvcCLg0AoUQmduzBIOXWMoSOxJYJWrk8LknBxro8bGS3NeqimsQBc6jBYp0QvKWU+\nWanJ8L/8dCmQKTTN5JJwbDzJSQKBgQDhaACQ6vGogDK951WmNQjo36r4aPjtpxVz\nC/oo5Lz4D+ujv+j16hthisCT9YjvAAI+ba+wrDgFYnwN/tpm18om8hRD5lr56zWd\n7SpC4c9K4gF9Sls48I7tGuaYyIptzZyoQetgrMSiOX6Z/rr++FsaiG2zLuzvknat\nBhaGzgQY+QKBgQCDg38wlV+Z61Jqcq6psE3IE4ChrtiLTVFUfivfHLI3Z8LWnjZA\nlYlU7X3huSivUPWtUmF2qkwU1C+atx2CCBDgnMqqluNIO1Y5hzKpb0JcZRdFroN5\n49AhFJpvHKkb9sMEZ2v3T4nEpplR0sJ80lJEI3gyBJMaF+i5+UEKwML20QKBgAue\nQrk1gvM+eF8Vd3KztRvbu4JGVt5ZCF//tAs7jwnyOCEemTFjubmTX03BECfRhkwV\nd0LR/ak5ZE+MXVGDueW8AJNnYjUTjJXxTYZZAUhI0e7VUFJlA9jw1AX4ADqcqIB7\ntVgsT45VDFN/r7/IqVl6wrzDpG7My9UrRSn5oHhxAoGAOYGXg/FB/GdaNKDNicrG\nB0wNjCLfQNtCA2iGY54ZF8byg8EbtBNsvnAZYXluAgIBSEbcwYWNwc4+yuZqlowu\nfPRe65LBYMaQCyRte38qni1z/kAV8nA/iYayI/BwErIRtM9Ik6CyXzL69DBqR+f6\nRhRkO5uF6ABnWjid6B2nSxM=\n-----END PRIVATE KEY-----\n",
  "client_email": "tester@mais-364217.iam.gserviceaccount.com",
  "client_id": "112856667075233794503",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/tester%40mais-364217.iam.gserviceaccount.com"
}
""")

                }
                else {
                    Toast.makeText(this, "nay", Toast.LENGTH_SHORT).show()

                }
            }

        //this is the button listener
        //inverted control model where it captures the image and passes it to the preview module
        //What we need is to pass it to the back end and not just the preview module
        snap.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            getPreviewImage.launch(takePictureIntent)
            /*startActivityForResult(takePictureIntent, REQUEST_CODE)*/
        }

        /*
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
            super.onActivityResult(requestCode,resultCode,data)
            if (requestCode== REQUEST_CODE && resultCode== Activity.RESULT_OK){
               val takenImage= data?.extras?.get("data")  as Bitmap
                imageView.setImageBitmap(takenImage)
                Toast.makeText(this, "yay", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "nay", Toast.LENGTH_SHORT).show()

            }
        }*/
    }
}