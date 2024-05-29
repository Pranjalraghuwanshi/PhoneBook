package com.example.phonebook

import ContactFragment
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_PICK = 1
    private var selectedImageUri: Uri? = null
    private lateinit var ivProfile:ImageView
    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private val contactList= mutableListOf<Contactclass>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)





        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            showCustomInputDialog()
        }


    }

private fun showCustomInputDialog(){
    val dialogView = layoutInflater.inflate(R.layout.dialogue_box_alert_layout, null)
    val builder = AlertDialog.Builder(this)
    builder.setView(dialogView)
    builder.setTitle("Add Contact")

    val btnselectimage=dialogView.findViewById<Button>(R.id.btn_profilepic)
    ivProfile=dialogView.findViewById(R.id.iv_profile)
     etName=dialogView.findViewById(R.id.et_name)
    etPhoneNumber=dialogView.findViewById(R.id.et_phone_number)


    Log.d("MainActivity", "EditText Name: $etName")
    Log.d("MainActivity", "EditText Phone: $etPhoneNumber")

    btnselectimage.setOnClickListener {
        val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,REQUEST_IMAGE_PICK)

    }
    builder.setPositiveButton("Save"){_,_ ->
        val name =etName.text.toString()
        val phone=etPhoneNumber.text.toString()



        Log.d("MainActivity", "Name: $name")
        Log.d("MainActivity", "Phone: $phone")

        val newContact = Contactclass(name, phone, selectedImageUri.toString())
        contactList.add(newContact)




        val contactFragment = ContactFragment.newInstance(name, phone, selectedImageUri.toString())
        supportFragmentManager.beginTransaction()
            .replace(androidx.fragment.R.id.fragment_container_view_tag, contactFragment)
            .commit()
    }
    builder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }

    builder.create().show()
}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
            } else {
                val source = ImageDecoder.createSource(this.contentResolver, selectedImageUri!!)
                ImageDecoder.decodeBitmap(source)
            }
            ivProfile.setImageBitmap(bitmap)
        }
    }
}

