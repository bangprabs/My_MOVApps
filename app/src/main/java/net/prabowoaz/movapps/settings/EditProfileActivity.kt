package net.prabowoaz.movapps.settings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.iv_profile
import kotlinx.android.synthetic.main.activity_sign_up_photo.*
import kotlinx.android.synthetic.main.image_picker_dialog.view.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.sign.signin.User
import net.prabowoaz.movapps.sign.singup.SignUpPhotoscreenActivity
import net.prabowoaz.movapps.utils.Preferences

class EditProfileActivity : AppCompatActivity(), PermissionListener {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sEmail: String

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    lateinit var storage: FirebaseStorage
    lateinit var storageRefrence: StorageReference
    lateinit var preferences: Preferences
    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = true
    lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        preferences = Preferences(this)
        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        edt_nameUpd.setText(preferences.getValues("nama"))
        edt_unameUpd.setText(preferences.getValues("username"))
        edt_emailUpd.setText(preferences.getValues("email"))
        edt_passwordUpd.setText(preferences.getValues("password"))

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        btn_saveData.setOnClickListener {
            sUsername = edt_unameUpd.text.toString().trim()
            sPassword = edt_passwordUpd.text.toString().trim()
            sNama = edt_nameUpd.text.toString().trim()
            sEmail = edt_emailUpd.text.toString().trim()

            if (sUsername.equals("")) {
                edt_unameUpd.error = "Silahkan Input Username Anda"
                edt_unameUpd.requestFocus()
            } else if (sPassword.equals("")) {
                edt_passwordUpd.error = "Silahkan Input Password Anda"
                edt_passwordUpd.requestFocus()
            } else if (sNama.equals("")) {
                edt_nameUpd.error = "Silahkan Input Nama Anda"
                edt_nameUpd.requestFocus()
            } else if (sEmail.equals("")) {
                edt_emailUpd.error = "Silahkan Input Email Anda"
                edt_emailUpd.requestFocus()
            } else {
                saveUser(sUsername, sPassword, sNama, sEmail)
            }
        }

        iv_delete.setOnClickListener {
            if (statusAdd) {
                statusAdd = true
                iv_add.setImageResource(R.drawable.btn_addimage)
                iv_profile.setImageResource(R.drawable.user_pic)
            } else {
                val mDialogView =
                    LayoutInflater.from(this).inflate(R.layout.image_picker_dialog, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Choose Action")
                val mAlertDialog = mBuilder.show()
                mAlertDialog.getWindow()
                    ?.setBackgroundDrawableResource(android.R.color.transparent);
                mDialogView.lytCameraPick.setOnClickListener {
                    mAlertDialog.dismiss()
                    choosePhotoFromCamera()
                }
                mDialogView.lytGalleryPick.setOnClickListener {
                    mAlertDialog.dismiss()
                    choosePhotoFromGallery()
                }
                mDialogView.tv_close.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }
    }

    private fun saveUser(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.nama = sNama
        user.password = sPassword

        if (sUsername != null) {
            checkingUsername(sUsername, user)
        }

//        mFirebaseDatabase.child(sUsername).setValue(user).addOnCompleteListener {
//            Toast.makeText(this@EditProfileActivity, "Data sudah di update", Toast.LENGTH_LONG)
//                .show()
//            finish()
//        }
    }

    private fun checkingUsername(iUsername: String, data: User) {
        mFirebaseDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                mFirebaseDatabase.child(iUsername).setValue(data)

                preferences.setValue("nama", data.nama.toString())
                preferences.setValue("user", data.username.toString())
                preferences.setValue("password", data.password.toString())
                preferences.setValue("username", data.username.toString())
                preferences.setValue("url", "")
                preferences.setValue("email", data.email.toString())
                preferences.setValue("status", "1")


                finish()
                Toast.makeText(this@EditProfileActivity, "Data sudah di update", Toast.LENGTH_LONG)
                    .show()
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProfileActivity, "" + error.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun choosePhotoFromCamera() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)
            .cameraOnly()
            .start()
    }

    private fun choosePhotoFromGallery() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)
            .galleryOnly()
            .start()
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        //To change body of created functions use File | Settings | File Templates.
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(this, "Anda tidak bisa menambahkan photo profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            statusAdd = true
            filePath = data?.data!!
            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

            btn_save.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.btn_hapus)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


}
