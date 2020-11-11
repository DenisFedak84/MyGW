package com.example.mygw.activity

import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.mygw.R
import com.example.mygw.test.Delegate
import com.example.mygw.utils.Utils
import com.example.mygw.utils.isEmailAddressValid
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.test_activity.*
import permissions.dispatcher.*
import java.io.File
import java.io.FileInputStream
import kotlin.random.Random

@RuntimePermissions
class TestActivity : ComponentActivity() {
    private lateinit var file: File

    val one = null
    val two = 1

    var p: String by Delegate()

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val path =
                    Utils.getRealPathApi19Above(
                        this,
                        uri
                    )
                file = File(path)

                saveInStorageWithPermissionCheck()
            }
        }

    private val storageRef = Firebase.storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if ("de@gmail.com".isEmailAddressValid())  // тест экстеншен


            imageSearchView.setOnClickListener(View.OnClickListener {
                getContent.launch("*/*")
            })

        imageUploadView.setOnClickListener(View.OnClickListener {
            getFromStorage()
        })


        var myLymbda: (Int, Int) -> String = { a: Int, b: Int ->
            var result = a + b
            result.toString()
        }

        myLymbda(3, 5) // тестируем лямбду
    }

    private fun getFromStorage() {
        storageRef.child("/Projects/Cultist/").listAll().addOnSuccessListener {
            it.items[0].downloadUrl.addOnSuccessListener {
                Glide.with(this)
                    .load(it)
                    .into(avatar)
            }.addOnFailureListener {
                textView.text = it.message
            }
        }
    }

    @NeedsPermission(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun saveInStorage() {
        if (file.isFile) {
            val randomName = Random.nextInt(0, 10000)

            val mountainsRef = storageRef.child("/Projects/Cultist/$randomName.jpg")
            val mountainImagesRef = storageRef.child("images/$randomName.jpg")

            val stream = FileInputStream(file)
            val uploadTask = mountainsRef.putStream(stream)
            uploadTask.addOnFailureListener {
                textView.text = "Error"
            }.addOnSuccessListener {
                textView.text = "Success"
            }
        } else {
            Toast.makeText(this, "file null", Toast.LENGTH_SHORT).show()
        }

    }

    @OnShowRationale(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun showRationaleForStorage(request: PermissionRequest) {
        showRationaleDialog(R.string.app_name, request)
    }

    @OnPermissionDenied(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onStorageDenied() {
        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onStorageNeverAskAgain() {
        Toast.makeText(this, "Permission never ask again", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
            .setPositiveButton("Ok") { _, _ -> request.proceed() }
            .setNegativeButton("Deny") { _, _ -> request.cancel() }
            .setCancelable(false)
            .setMessage(messageResId)
            .show()
    }
}