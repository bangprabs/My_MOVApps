package net.prabowoaz.movapps.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet.rc_history
import kotlinx.android.synthetic.main.activity_my_wallet.tv_saldo
import kotlinx.android.synthetic.main.fragment_dashboard.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.checkout.model.Checkout
import net.prabowoaz.movapps.home.tiket.TiketAdapter
import net.prabowoaz.movapps.mywallet.adapter.HistoryAdapter
import net.prabowoaz.movapps.mywallet.model.Transaction
import net.prabowoaz.movapps.utils.Preferences
import java.text.NumberFormat
import java.util.*

class MyWalletActivity : AppCompatActivity() {

    lateinit var preferences: Preferences
    private var dataList = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        preferences = Preferences(this)

        if (!preferences.getValues("saldo").equals("")){
            currecy(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        rc_history.layoutManager = LinearLayoutManager(this)

        dataList.add(Transaction("Avengers :  End Game", "Sabtu, 20 Januari 2019", "70000"))
        dataList.add(Transaction("Top Up", "Senin, 23 April 2019", "450000"))
        dataList.add(Transaction("Cars 3", "Sabtu, 14 Mei 2019", "85000"))

        rc_history.adapter = HistoryAdapter(dataList) {

        }
    }

    private fun currecy(harga:Double, textView: TextView) {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        textView.setText(formatRupiah.format(harga as Double))
    }
}
