package com.ankit.foodfie.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.ankit.foodfie.*
import com.ankit.foodfie.fragment.*
import com.ankit.foodfie.fragment.AboutFragment
import com.ankit.foodfie.fragment.FavoriteFragment
import com.ankit.foodfie.fragment.ProfileFragment
import com.ankit.foodfie.util.ConnectionManager
import com.google.android.material.navigation.NavigationView

@Suppress("NAME_SHADOWING")
class HomeActivity : AppCompatActivity() {

    private lateinit var drawLayout: DrawerLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var toolbar: Toolbar
    private lateinit var frameLayout: FrameLayout
    private lateinit var navigationView: NavigationView
    private var previousMenuItem: MenuItem? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)

        setToolBar()
        val actionDrawerToggle = ActionBarDrawerToggle(
            this@HomeActivity,
            drawLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawLayout.addDrawerListener(actionDrawerToggle)
        actionDrawerToggle.syncState()
        openDashBoard()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem != null ) {
                previousMenuItem?.isChecked = false
            }
                it.isCheckable = true
                it.isChecked = true
                previousMenuItem = it

            when (it.itemId) {
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment())
                        .commit()
                    supportActionBar?.title = "Profile"
                    drawLayout.closeDrawers()
                }
                R.id.dashBoard -> {
                    openDashBoard()
                    drawLayout.closeDrawers()
                }
                R.id.favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, FavoriteFragment())
                        .commit()
                    supportActionBar?.title = "Favorite"
                    drawLayout.closeDrawers()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, AboutFragment())
                        .commit()
                    supportActionBar?.title = "About "
                    drawLayout.closeDrawers()
                }
                R.id.faq ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,FAQFragment())
                        .commit()
                    supportActionBar?.title = "FAQ"
                    drawLayout.closeDrawers()
                }
                R.id.history ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,HistoryFragment())
                        .commit()
                    supportActionBar?.title = "History"
                    drawLayout.closeDrawers()
                }
                R.id.log_out ->{
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle("Log out")
                    alertDialog.setMessage("Are sure you want to log out?")
                    alertDialog.setPositiveButton("Yes"){text,listener ->
                        startActivity(Intent(this@HomeActivity,LoginActivity::class.java))
                        applicationContext.getSharedPreferences("RegistrationPreference",Context.MODE_PRIVATE).edit().clear().apply()
                        finish()
                    }
                    alertDialog.setNegativeButton("No"){text,listener ->
                    }
                    alertDialog.create()
                    alertDialog.show()


                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun setToolBar() {
        setSupportActionBar(toolbar)

        val button = toolbar.findViewById<ImageButton>(R.id.btCheckInternet)
        button.setOnClickListener{
            if(ConnectionManager().connectionStatus(this as Context)){
                val alertDialog = AlertDialog.Builder(this as Context)
                alertDialog.setTitle("Internet Status")
                alertDialog.setMessage("Connection Found")
                alertDialog.setNegativeButton("Close"){text,context ->

                }
                alertDialog.create()
                alertDialog.show()
            }
            else{
                val alertDialog = AlertDialog.Builder(this as Context)
                alertDialog.setTitle("Internet Status")
                alertDialog.setMessage("Connection Not Found")
                alertDialog.setNegativeButton("Close"){text,context ->

                }
                alertDialog.create()
                alertDialog.show()
            }
        }



        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) drawLayout.openDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)

    }

    private fun openDashBoard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout,DashBoardFragment())
            .commit()
        supportActionBar?.title = "DashBoard"
        navigationView.setCheckedItem(R.id.dashBoard)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.frameLayout)){
            !is DashBoardFragment -> openDashBoard()
            else -> super.onBackPressed()
        }
    }



}



