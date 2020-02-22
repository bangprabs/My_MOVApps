package net.prabowoaz.movapps.settings


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_setting.*
import net.prabowoaz.movapps.mywallet.MyWalletActivity
import net.prabowoaz.movapps.utils.Preferences
import net.prabowoaz.movapps.sign.signin.SignInActivity
import android.R
import android.provider.Settings
import android.widget.Toast


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {

    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(net.prabowoaz.movapps.R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_help.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://bangprabs.github.io/"))
            startActivity(intent)
        }

        tv_edt_profile.setOnClickListener {
            val mIntent = Intent(context!!, EditProfileActivity::class.java)
            startActivity(mIntent)
        }

        tv_my_wallet.setOnClickListener {
            val mIntent = Intent(context!!, MyWalletActivity::class.java)
            startActivity(mIntent)
        }

        tv_chg_languge.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }


        preferences = Preferences(context!!.applicationContext)

        tv_nama.text = preferences.getValues("nama")
        tv_email.text = preferences.getValues("email")

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)
    }

}
