package com.example.tp01_leo_hotz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

data class CinemaQuestion(
    val question: String,
    val reponse: List<String>,
    val resultat: Int,
)


/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var theme: String? = null

    private val cinemaQuestions = listOf(
        CinemaQuestion(
            question = "Quel est le nom du film où Marty McFly est le personnage principal ?",
            reponse = listOf("Retour vers le futur", "Star Wars"),
            resultat = 0
        ),
        CinemaQuestion(
            question = "Quel acteur a joué le personnage principal dans le film 'Forrest Gump' ?",
            reponse = listOf("Tom Hanks", "Leonardo DiCaprio"),
            resultat = 0
        ),
        CinemaQuestion(
            question = "Dans quel film trouve-t-on le personnage de Jack Dawson ?",
            reponse = listOf("Titanic", "Inception"),
            resultat = 0
        ),
        CinemaQuestion(
            question = "Qui a réalisé le film 'Pulp Fiction' ?",
            reponse = listOf("Steven Spielberg", "Quentin Tarantino"),
            resultat = 1
        ),
        CinemaQuestion(
            question = "Quelle est la couleur de la pilule que Neo prend dans 'Matrix' ?",
            reponse = listOf("Rouge", "Bleu"),
            resultat = 0
        ),
        CinemaQuestion(
            question = "Dans quel film voit-on un parc rempli de dinosaures recréés génétiquement ?",
            reponse = listOf("Jurassic Park", "King Kong"),
            resultat = 0
        ),
        CinemaQuestion(
            question = "Quel film d'animation met en scène une famille de super-héros ?",
            reponse = listOf("Toy Story", "Les Indestructibles"),
            resultat = 1
        ),
        CinemaQuestion(
            question = "Quel est le titre du premier film de la saga 'Harry Potter' ?",
            reponse = listOf("Harry Potter à l'école des sorciers", "Harry Potter et la chambre des secrets"),
            resultat = 0
        ),
        CinemaQuestion(
            question = "Quel acteur a interprété le Joker dans 'The Dark Knight' ?",
            reponse = listOf("Heath Ledger", "Joaquin Phoenix"),
            resultat = 0
        ),
        CinemaQuestion(
            question = "Quel film raconte l'histoire de la vie de Jordan Belfort ?",
            reponse = listOf("Le Loup de Wall Street", "Le Parrain"),
            resultat = 0
        )

    )
    private var numQuestionTv: TextView?= null
    private var questionTv: TextView?= null
    private var scoreTv: TextView?= null
    private var response1Btn: Button? = null
    private var response2Btn: Button? = null
    private var currentQuestion : Int = 0
    private var score = 0;



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val arguments = arguments
        theme = arguments?.getString("theme")
        val themeTv : TextView = view.findViewById(R.id.themeTv)
        themeTv.text = theme
        numQuestionTv = view.findViewById(R.id.numQuestionTv)
        questionTv = view.findViewById(R.id.questionTv)
        response1Btn = view.findViewById(R.id.response1Btn)
        response2Btn = view.findViewById(R.id.response2Btn)
        scoreTv = view.findViewById(R.id.scoreTv)

        val localResponse1Btn = response1Btn
        val localResponse2Btn = response2Btn

        localResponse1Btn?.setOnClickListener {
            verifyResponse(0)
        }
        localResponse2Btn?.setOnClickListener {
            verifyResponse(1)
        }

        startGame()

        // Inflate the layout for this fragment
        return view
    }

    fun startGame(){
        setQuestion(currentQuestion)
    }

    fun setQuestion(number : Int){
        questionTv?.text = cinemaQuestions[number].question
        response1Btn?.text = cinemaQuestions[number].reponse[0]
        response2Btn?.text = cinemaQuestions[number].reponse[1]
    }

    fun verifyResponse(response: Int ){
        val expectedResponse = cinemaQuestions[currentQuestion].resultat
        if(response == expectedResponse){
            incrementeScore()
        }
        nextQuestion()
        setQuestion(currentQuestion)
    }

    fun nextQuestion(){
        if(currentQuestion < 9){
            currentQuestion++
            val numQuestion = currentQuestion + 1
            numQuestionTv?.text = numQuestion.toString()
        }
        else{
            endGame()
        }
    }

    fun incrementeScore(){
        score++
        scoreTv?.text = score.toString()
    }

    private fun endGame() {
        val resultFragment = FragmentResult()

        val bundle = Bundle()
        bundle.putString("theme", theme.toString())
        bundle.putString("score", score.toString())
        resultFragment.arguments = bundle

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentView, resultFragment)
        transaction.addToBackStack(null) // this allows you to go back to the previous fragment
        transaction.commit()
    }



    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            QuestionFragment()
    }
}