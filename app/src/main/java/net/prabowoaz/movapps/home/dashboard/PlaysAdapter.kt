package net.prabowoaz.movapps.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bagicode.bwamov.home.model.Film
import com.bagicode.bwamov.home.model.Plays
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.ValueEventListener
import net.prabowoaz.movapps.R

class PlaysAdapter(private var data: List<Plays>, private val listener: (Plays) -> Unit) :
    RecyclerView.Adapter<PlaysAdapter.LeagueViewHolder>() {

    lateinit var ContextAdapter : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeagueViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        ContextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.row_item_play, parent, false)
        return LeagueViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, ContextAdapter, position)
    }

    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvPlay: TextView = view.findViewById(R.id.tv_player)
        private val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Plays, listener: (Plays) -> Unit, context : Context, position : Int) {
            tvPlay.text = data.nama

            Glide.with(context)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

}