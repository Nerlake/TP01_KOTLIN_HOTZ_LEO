package com.example.tp01_leo_hotz

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class GameActivity : AppCompatActivity() {

    private var fragmentMenu : ListeQuizz = ListeQuizz.newInstance()
    private var fragmentGame : QuestionFragment = QuestionFragment.newInstance()




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        changeFragmentTo(fragmentMenu)

    }

    private fun changeFragmentTo(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentView, fragment).commit();
    }






}