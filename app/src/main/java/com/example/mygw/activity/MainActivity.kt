package com.example.mygw.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.mygw.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureToolbar()
        drawerLayout = findViewById(R.id.drawer_layout)
        navController = findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupNavigation()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupNavigation() {
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout) // Добавляем интеграцию ActionBar.
        NavigationUI.setupWithNavController(navigationView, navController) // вешаем обработчик нажатий на дровер
    }

    override fun onSupportNavigateUp(): Boolean { // тулбар кнопка назад и гамбургер
        return NavigationUI.navigateUp(navController,drawer_layout)
    }
}