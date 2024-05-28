package com.example.tp01_leo_hotz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.tp01_leo_hotz.datas.AppDatabase
import com.example.tp01_leo_hotz.datas.Question
import com.example.tp01_leo_hotz.enums.Categories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var pseudo: EditText
    private lateinit var database: AppDatabase

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

        // Initialisation de la base de données
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    fun connectToGame(view: View) {
        val playerName = pseudo.text.toString()
        savePlayerName(this, playerName)
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun savePlayerName(context: Context, playerName: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("PlayerPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("playerName", playerName)
        editor.apply()

        CoroutineScope(Dispatchers.IO).launch {

            database.questionDao().deleteAllQuestions()
            createAllQuestions()
        }

    }


    private fun createAllQuestions(){

        val questions = listOf(
            Question(
                question = "Quel est le nom du plombier moustachu, personnage principal de nombreux jeux Nintendo ?",
                reponse = listOf("Luigi", "Mario"),
                resultat = 1,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Dans quel jeu incarne-t-on un chasseur de trésors nommé Nathan Drake ?",
                reponse = listOf("Uncharted", "Tomb Raider"),
                resultat = 0,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel jeu de combat met en scène des personnages comme Ryu et Chun-Li ?",
                reponse = listOf("Tekken", "Street Fighter"),
                resultat = 1,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel est le titre du jeu où les joueurs construisent et explorent des mondes en blocs ?",
                reponse = listOf("Minecraft", "Roblox"),
                resultat = 0,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Dans quel jeu peut-on jouer avec des équipes de champions pour détruire le Nexus ennemi ?",
                reponse = listOf("Dota 2", "League of Legends"),
                resultat = 1,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel jeu de survie post-apocalyptique met en scène Joel et Ellie ?",
                reponse = listOf("Days Gone", "The Last of Us"),
                resultat = 1,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel jeu se déroule dans une ville fictive appelée Los Santos ?",
                reponse = listOf("Watch Dogs 2", "Grand Theft Auto V"),
                resultat = 1,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel jeu de simulation permet aux joueurs de gérer leur propre île déserte ?",
                reponse = listOf("Animal Crossing: New Horizons", "Stardew Valley"),
                resultat = 0,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel jeu d'action-aventure présente un héros nommé Link dans le royaume d'Hyrule ?",
                reponse = listOf("Final Fantasy", "The Legend of Zelda"),
                resultat = 1,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel jeu de tir à la première personne est célèbre pour son mode Battle Royale ?",
                reponse = listOf("Fortnite", "Counter-Strike"),
                resultat = 0,
                category = Categories.JEUX_VIDEOS.toString()
            ),
            Question(
                question = "Quel est le nom du film où Marty McFly est le personnage principal ?",
                reponse = listOf("Retour vers le futur", "Star Wars"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Quel acteur a joué le personnage principal dans le film 'Forrest Gump' ?",
                reponse = listOf("Tom Hanks", "Leonardo DiCaprio"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Dans quel film trouve-t-on le personnage de Jack Dawson ?",
                reponse = listOf("Titanic", "Inception"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Qui a réalisé le film 'Pulp Fiction' ?",
                reponse = listOf("Steven Spielberg", "Quentin Tarantino"),
                resultat = 1,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Quelle est la couleur de la pilule que Neo prend dans 'Matrix' ?",
                reponse = listOf("Rouge", "Bleu"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Dans quel film voit-on un parc rempli de dinosaures recréés génétiquement ?",
                reponse = listOf("Jurassic Park", "King Kong"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Quel film d'animation met en scène une famille de super-héros ?",
                reponse = listOf("Toy Story", "Les Indestructibles"),
                resultat = 1,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Quel est le titre du premier film de la saga 'Harry Potter' ?",
                reponse = listOf("Harry Potter à l'école des sorciers", "Harry Potter et la chambre des secrets"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Quel acteur a interprété le Joker dans 'The Dark Knight' ?",
                reponse = listOf("Heath Ledger", "Joaquin Phoenix"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Quel film raconte l'histoire de la vie de Jordan Belfort ?",
                reponse = listOf("Le Loup de Wall Street", "Le Parrain"),
                resultat = 0,
                category = Categories.CINEMA.toString()
            ),
            Question(
                question = "Quel groupe a chanté la chanson 'Bohemian Rhapsody' ?",
                reponse = listOf("The Beatles", "Queen"),
                resultat = 1,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel chanteur est connu comme le 'King of Pop' ?",
                reponse = listOf("Michael Jackson", "Elvis Presley"),
                resultat = 0,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel artiste a sorti l'album '25' en 2015 ?",
                reponse = listOf("Adele", "Beyoncé"),
                resultat = 0,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel groupe est célèbre pour la chanson 'Stairway to Heaven' ?",
                reponse = listOf("Pink Floyd", "Led Zeppelin"),
                resultat = 1,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel rappeur a interprété 'Lose Yourself' pour le film '8 Mile' ?",
                reponse = listOf("Eminem", "Jay-Z"),
                resultat = 0,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quelle chanteuse est connue pour son alter ego Sasha Fierce ?",
                reponse = listOf("Rihanna", "Beyoncé"),
                resultat = 1,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel groupe a sorti l'album 'The Dark Side of the Moon' ?",
                reponse = listOf("The Rolling Stones", "Pink Floyd"),
                resultat = 1,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel artiste a chanté 'Purple Rain' ?",
                reponse = listOf("Prince", "David Bowie"),
                resultat = 0,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel groupe est connu pour la chanson 'Wonderwall' ?",
                reponse = listOf("Oasis", "Blur"),
                resultat = 0,
                category = Categories.MUSIQUE.toString()
            ),
            Question(
                question = "Quel chanteur a popularisé la chanson 'Rocket Man' ?",
                reponse = listOf("Elton John", "Billy Joel"),
                resultat = 0,
                category = Categories.MUSIQUE.toString()
            )


        )

        CoroutineScope(Dispatchers.IO).launch {
            database.questionDao().insert(*questions.toTypedArray())
        }
    }
}
