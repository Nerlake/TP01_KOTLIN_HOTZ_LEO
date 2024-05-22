package com.example.tp01_leo_hotz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //var pseudo: EditText? = null
    private lateinit var pseudo: EditText
    //private lateinit var playerNameTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pseudo = findViewById(R.id.pseudoEt)


    }

    fun connectToGame(view: View) {

        val playerName = pseudo.text.toString()
        savePlayerName(this, playerName)
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent);
    }

    fun savePlayerName(context: Context, playerName: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("PlayerPrefs", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("playerName", playerName)

        editor.apply()
    }
}