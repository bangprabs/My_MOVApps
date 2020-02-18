package net.prabowoaz.movapps.home.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagicode.bwamov.home.model.Film
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_history.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.home.dashboard.ComingSoonAdapter
import net.prabowoaz.movapps.home.tiket.TiketActivity
import net.prabowoaz.movapps.utils.Preferences

class HistoryActivity : AppCompatActivity() {

    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        preferences = Preferences(this)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        rc_history.layoutManager = LinearLayoutManager(this)
        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {
                    val film = getdataSnapshot.getValue(Film::class.java!!)
                    dataList.add(film!!)
                }
                rc_history.adapter = ComingSoonAdapter(dataList) {
                    val intent =
                        Intent(this@HistoryActivity, TiketActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
                tv_totalHistory.setText(dataList.size.toString() + " Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HistoryActivity, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
