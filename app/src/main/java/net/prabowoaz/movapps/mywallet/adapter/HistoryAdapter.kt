package net.prabowoaz.movapps.mywallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_my_wallet.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.mywallet.model.Transaction
import net.prabowoaz.movapps.utils.Preferences
import java.text.NumberFormat
import java.util.*



class HistoryAdapter(
    private var data: List<Transaction>,
    private val listener: (Transaction) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter: Context
    lateinit var preferences: Preferences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        ContextAdapter = parent.context
        val inflatedView: View =
            layoutInflater.inflate(R.layout.row_history_transaction, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvInfo: TextView = view.findViewById(R.id.tv_information)
        private val tvDate: TextView = view.findViewById(R.id.tv_date)
        private val tvNominal = view.findViewById(R.id.tv_nominal) as TextView

        fun bindItem(
            data: Transaction,
            listener: (Transaction) -> Unit,
            context: Context,
            position: Int
        ) {

            tvInfo.text = data.information
            tvDate.text = data.date
            currecy(data.nominal!!.toDouble(), tvNominal)
        }

        private fun currecy(harga:Double, textView: TextView) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            textView.setText(formatRupiah.format(harga as Double))
        }
    }
}