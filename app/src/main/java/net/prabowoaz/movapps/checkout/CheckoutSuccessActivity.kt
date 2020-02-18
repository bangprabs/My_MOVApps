package net.prabowoaz.movapps.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bagicode.bwamov.home.model.Film
import kotlinx.android.synthetic.main.activity_checkout_success.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.home.HomeActivity
import net.prabowoaz.movapps.home.tiket.TiketActivity

class CheckoutSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)


        btn_home.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@CheckoutSuccessActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
