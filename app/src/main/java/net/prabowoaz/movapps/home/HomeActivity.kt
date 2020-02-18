package net.prabowoaz.movapps.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*
import net.prabowoaz.movapps.R
import net.prabowoaz.movapps.home.dashboard.DashboardFragment
import net.prabowoaz.movapps.home.tiket.TiketFragment
import net.prabowoaz.movapps.settings.SettingFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentTiket = TiketFragment()
        val settingFragment = SettingFragment()
        val fragmentHome = DashboardFragment()

        setFragment(fragmentHome)

        iv_home.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(iv_home, R.drawable.ic_home_hover)
            changeIcon(iv_ticket, R.drawable.ic_ticket)
            changeIcon(iv_profile, R.drawable.ic_profile)
        }

        iv_ticket.setOnClickListener {
            setFragment(fragmentTiket)
            changeIcon(iv_home, R.drawable.ic_home)
            changeIcon(iv_ticket, R.drawable.ic_ticket_hover)
            changeIcon(iv_profile, R.drawable.ic_profile)
        }

        iv_profile.setOnClickListener {
            setFragment(settingFragment)
            changeIcon(iv_home, R.drawable.ic_home)
            changeIcon(iv_ticket, R.drawable.ic_ticket)
            changeIcon(iv_profile, R.drawable.ic_profile_hover)
        }
    }

    protected fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.layout_frame, fragment)
        fragmentTransition.commit()
    }
    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}
