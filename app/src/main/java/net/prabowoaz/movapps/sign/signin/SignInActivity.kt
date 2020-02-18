package net.prabowoaz.movapps.sign.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.prabowoaz.movapps.R
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import net.prabowoaz.movapps.home.HomeActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import net.prabowoaz.movapps.sign.singup.SignUpActivity
import net.prabowoaz.movapps.utils.Preferences

class SignInActivity : AppCompatActivity() {

    lateinit var iUsername: String
    lateinit var iPassword: String
    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        preferences.setValue("onboarding", "1")
        if (preferences.getValues("status").equals("1")) {
            finishAffinity()
            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        btn_home.setOnClickListener {
            iUsername = edt_uname.text.toString()
            iPassword = edt_pass.text.toString()

            if (iUsername.equals("")) {
                edt_uname.error = getString(R.string.alert_uname)
                edt_uname.requestFocus()
            } else if (iPassword.equals("")) {
                edt_pass.error = getString(R.string.alert_pass)
                edt_pass.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }
        btn_regist.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, getString(R.string.find_user), Toast.LENGTH_LONG)
                        .show()
                } else {
                    if (user.password.equals(iPassword)) {
                        Toast.makeText(this@SignInActivity, getString(R.string.welcome_toast), Toast.LENGTH_LONG)
                            .show()

                        preferences.setValue("nama", user.nama.toString())
                        preferences.setValue("user", user.username.toString())
                        preferences.setValue("username", user.username.toString())
                        preferences.setValue("password", user.username.toString())
                        preferences.setValue("url", user.url.toString())
                        preferences.setValue("email", user.email.toString())
                        preferences.setValue("saldo", user.saldo.toString())
                        preferences.setValue("status", "1")

                        finishAffinity()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            getString(R.string.toast_wrongpass),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}




