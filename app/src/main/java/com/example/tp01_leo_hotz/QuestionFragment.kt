package com.example.tp01_leo_hotz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

data class CinemaQuestion(
    val question: String,
    val reponse: List<String>,
    val resultat: Int,
)

class QuestionFragment : Fragment() {
    private var theme: String? = null

    private lateinit var cinemaQuestions: List<CinemaQuestion>
    private var numQuestionTv: TextView? = null
    private var questionTv: TextView? = null
    private var scoreTv: TextView? = null
    private var response1Btn: Button? = null
    private var response2Btn: Button? = null
    private var currentQuestion: Int = 0
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val arguments = arguments
        theme = arguments?.getString("theme")
        val themeTv: TextView = view.findViewById(R.id.themeTv)
        themeTv.text = theme
        numQuestionTv = view.findViewById(R.id.numQuestionTv)
        questionTv = view.findViewById(R.id.questionTv)
        response1Btn = view.findViewById(R.id.response1Btn)
        response2Btn = view.findViewById(R.id.response2Btn)
        scoreTv = view.findViewById(R.id.scoreTv)

        response1Btn?.setOnClickListener {
            verifyResponse(0)
        }
        response2Btn?.setOnClickListener {
            verifyResponse(1)
        }

        cinemaQuestions = loadCinemaQuestions(requireContext())
        startGame()

        return view
    }

    private fun startGame() {
        setQuestion(currentQuestion)
    }

    private fun setQuestion(number: Int) {
        questionTv?.text = cinemaQuestions[number].question
        response1Btn?.text = cinemaQuestions[number].reponse[0]
        response2Btn?.text = cinemaQuestions[number].reponse[1]
    }

    private fun verifyResponse(response: Int) {
        val expectedResponse = cinemaQuestions[currentQuestion].resultat
        if (response == expectedResponse) {
            incrementScore()
        }
        nextQuestion()
        setQuestion(currentQuestion)
    }

    private fun nextQuestion() {
        if (currentQuestion < cinemaQuestions.size - 1) {
            currentQuestion++
            val numQuestion = currentQuestion + 1
            numQuestionTv?.text = numQuestion.toString()
        } else {
            endGame()
        }
    }

    private fun incrementScore() {
        score++
        scoreTv?.text = score.toString()
    }

    private fun endGame() {
        val resultFragment = FragmentResult()

        val bundle = Bundle()
        bundle.putString("theme", theme)
        bundle.putString("score", score.toString())
        resultFragment.arguments = bundle

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentView, resultFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun loadCinemaQuestions(context: Context): List<CinemaQuestion> {
        var inputStream = context.resources.openRawResource(R.raw.cinema)
        when{
            theme == "Cinema" -> inputStream = context.resources.openRawResource(R.raw.cinema)
            theme == "Jeux-video" -> inputStream = context.resources.openRawResource(R.raw.jeuxvideo)
            theme == "Musique" -> inputStream = context.resources.openRawResource(R.raw.musique)
        }

        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<CinemaQuestion>>() {}.type
        return Gson().fromJson(reader, type)
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuestionFragment()
    }
}
