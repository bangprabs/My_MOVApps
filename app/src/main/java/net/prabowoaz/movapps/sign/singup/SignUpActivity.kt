package net.prabowoaz.movapps.sign.singup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_in.edt_uname
import kotlinx.android.synthetic.main.activity_sign_up.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.sign.signin.User
import net.prabowoaz.movapps.utils.Preferences

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sNama: String
    lateinit var sEmail: String

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        btn_registr.setOnClickListener {
            sUsername = edt_unameReg.text.toString()
            sPassword = edt_password.text.toString()
            sNama = edt_name.text.toString()
            sEmail = edt_email.text.toString()

            if (sUsername.equals("")) {
                edt_uname.error = "Silahkan Input Username Anda"
                edt_uname.requestFocus()
            } else if (sPassword.equals("")) {
                edt_password.error = "Silahkan Input Password Anda"
                edt_password.requestFocus()
            } else if (sNama.equals("")) {
                edt_name.error = "Silahkan Input Nama Anda"
                edt_name.requestFocus()
            } else if (sEmail.equals("")) {
                edt_email.error = "Silahkan Input Email Anda"
                edt_email.requestFocus()
            } else {
                saveUser(sUsername, sPassword, sNama, sEmail)
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
    }

    private fun checkingUsername(iUsername: String, data: User) {
        mFirebaseDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mFirebaseDatabase.child(iUsername).setValue(data)

                    preferences.setValue("nama", data.nama.toString())
                    preferences.setValue("user", data.username.toString())
                    preferences.setValue("password", data.password.toString())
                    preferences.setValue("username", data.username.toString())
                    preferences.setValue("url", "")
                    preferences.setValue("email", data.email.toString())
                    preferences.setValue("status", "1")

                    val intent =
                        Intent(this@SignUpActivity, SignUpPhotoscreenActivity::class.java).putExtra(
                            "nama",
                            data.nama
                        )
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUpActivity, "User Sudah Digunakan", Toast.LENGTH_LONG)
                        .show()
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, "" + error.message, Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}

