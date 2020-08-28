package com.example.mygw.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
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
//        appBarConfiguration = AppBarConfiguration(navController.graph) - дефолтное поведение дровера
        appBarConfiguration = AppBarConfiguration(setOf(R.id.projectFragment, R.id.paintFragment), drawerLayout) // убираем стрелки назад(всегда гамбургер)
        setupNavigation()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun setupNavigation() {
        setupActionBarWithNavController(navController,appBarConfiguration)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout) // Добавляем интеграцию ActionBar.
        NavigationUI.setupWithNavController(navigationView, navController) // вешаем обработчик нажатий на дровер

    }

    override fun onSupportNavigateUp(): Boolean { // тулбар кнопка назад и гамбургер
//        return NavigationUI.navigateUp(navController,drawer_layout) - дефолтное поведение
        if (navController.currentDestination!!.id == R.id.paintFragment){
//            finish() // закрываем активити если нажимаешь назад на последнем фрагменте
            drawerLayout.open()
        }
        return navController.navigateUp(appBarConfiguration)
    }
}