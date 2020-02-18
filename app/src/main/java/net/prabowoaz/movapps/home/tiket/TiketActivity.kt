package net.prabowoaz.movapps.home.tiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.bwamov.home.model.Film
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_checkout.rc_checkout
import kotlinx.android.synthetic.main.activity_tiket.*
import kotlinx.android.synthetic.main.barcode_dialog.view.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.checkout.model.Checkout

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        val data = intent.getParcelableExtra<Film>("data")

        tv_title.text = data.judul
        tv_genre.text = data.genre
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster_image)

        rc_checkout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1", ""))
        dataList.add(Checkout("C2", ""))

        rc_checkout.adapter = TiketAdapter(dataList) {

        }

        iv_barcode.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this).inflate(R.layout.barcode_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
            mDialogView.btn_close.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }
}
