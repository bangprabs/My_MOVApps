package net.prabowoaz.movapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.bwamov.home.model.Film
import com.bagicode.bwamov.home.model.Plays
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail.*
import net.prabowoaz.movapps.checkout.PilihBangkuActivity
import net.prabowoaz.movapps.home.dashboard.PlaysAdapter

class DetailActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent. getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
            .child(data.judul.toString())
            .child("play")

        tv_judul.text = data.judul
        tv_genre.text = data.genre
        tv_desc.text = data.desc
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster)

        btn_pilih_bangku.setOnClickListener {
            val intent =
                Intent(this@DetailActivity, PilihBangkuActivity::class.java).putExtra("data",data)
            startActivity(intent)
        }
        iv_back.setOnClickListener {
            finish()
        }
        rv_whoplayed.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val film = getdataSnapshot.getValue(Plays::class.java!!)
                    dataList.add(film!!)
                }
                rv_whoplayed.adapter = PlaysAdapter(dataList) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailActivity, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
