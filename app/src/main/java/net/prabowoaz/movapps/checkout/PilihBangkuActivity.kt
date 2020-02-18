package net.prabowoaz.movapps.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bagicode.bwamov.home.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.checkout.model.Checkout

class PilihBangkuActivity : AppCompatActivity() {

    var statusA3: Boolean = false
    var statusA4: Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>("data")

        tv_judul.text = data.judul

        a5.setOnClickListener {
            if (statusA3) {
                a5.setImageResource(R.drawable.ic_empty)
                statusA3 = false
                total -= 1
                belitiket(total)

            } else {
                a5.setImageResource(R.drawable.ic_selected)
                statusA3 = true
                total += 1
                belitiket(total)

                val data = Checkout("A3", "70000")
                dataList.add(data)
            }
        }

        a6.setOnClickListener {
            if (statusA4) {
                a6.setImageResource(R.drawable.ic_empty)
                statusA4 = false
                total -= 1
                belitiket(total)
            } else {
                a6.setImageResource(R.drawable.ic_selected)
                statusA4 = true
                total += 1
                belitiket(total)

                val data = Checkout("A4", "70000")
                dataList.add(data)
            }
        }

        btn_home.setOnClickListener {

            val intent = Intent(
                this,
                CheckoutActivity::class.java
            ).putExtra("data", dataList).putExtra("datas", data)
            startActivity(intent)
        }

    }

    private fun belitiket(total: Int) {
        if (total == 0) {
            btn_home.setText("Beli Tiket")
            btn_home.visibility = View.INVISIBLE
        } else {
            btn_home.setText("Beli Tiket (" + total + ")")
            btn_home.visibility = View.VISIBLE
        }

    }
}

