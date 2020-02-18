package net.prabowoaz.movapps.home.tiket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.checkout.model.Checkout

class TiketAdapter(
    private var data: List<Checkout>,
    private val listener: (Checkout) -> Unit
) : RecyclerView.Adapter<TiketAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View =
            layoutInflater.inflate(R.layout.row_item_checkout_white, parent, false)

        return LeagueViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle: TextView = view.findViewById(R.id.tv_kursi)


        fun bindItem(
            data: Checkout,
            listener: (Checkout) -> Unit,
            context: Context,
            position: Int
        ) {

            tvTitle.text = context.getString(R.string.seat_number) + " " + data.kursi


            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}

