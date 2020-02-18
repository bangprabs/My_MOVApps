package net.prabowoaz.movapps.checkout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.checkout.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(
    private var data: List<Checkout>,
    private val listener: (Checkout) -> Unit
) :
    RecyclerView.Adapter<CheckoutAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)

        return LeagueViewHolder(inflatedView)
    }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvKursi: TextView = view.findViewById(R.id.tv_kursi)
        private val tvHarga: TextView = view.findViewById(R.id.tv_harga)

        fun bindItem(
            data: Checkout,
            listener: (Checkout) -> Unit,
            context: Context,
            position: Int)
        {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            tvHarga.setText(formatRupiah.format(data.harga!!.toDouble()))

            if (data.kursi!!.startsWith("Total")) {
                tvKursi.text = data.kursi
                tvKursi.setCompoundDrawables(null, null, null, null)
            } else {
                tvKursi.text = "Seat No. " + data.kursi
            }
            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}
