package com.example.tp01_leo_hotz

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.tp01_leo_hotz.datas.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionFragment : Fragment() {
    private var theme: String? = null

    private lateinit var questions: List<Question>
    private var numQuestionTv: TextView? = null
    private var questionTv: TextView? = null
    private var timerTv: TextView? = null
    private var scoreTv: TextView? = null
    private var response1Btn: Button? = null
    private var response2Btn: Button? = null
    private var currentQuestion: Int = 0
    private var score = 0
    private var countDownTimer: CountDownTimer? = null

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
        timerTv = view.findViewById(R.id.timerTv)

        response1Btn?.setOnClickListener {
            verifyResponse(0)
        }
        response2Btn?.setOnClickListener {
            verifyResponse(1)
        }

        loadQuestionsByCategory(requireContext(), theme ?: "")

        return view
    }

    private fun loadQuestionsByCategory(context: Context, category: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = MyApp.database.questionDao().getAllByCategorie(category)
            withContext(Dispatchers.Main) {
                questions = response.toList()
                startGame()
            }
        }
    }

    private fun startGame() {
        if (questions.isNotEmpty()) {
            setQuestion(currentQuestion)
            startTimer()
        }
    }

    private fun setQuestion(number: Int) {
        if (questions.isNotEmpty()) {
            questionTv?.text = questions[number].question
            response1Btn?.text = questions[number].reponse[0]
            response2Btn?.text = questions[number].reponse[1]
        }
    }

    private fun verifyResponse(response: Int) {
        val expectedResponse = questions[currentQuestion].resultat
        if (response == expectedResponse) {
            incrementScore()
        }
        nextQuestion()
        setQuestion(currentQuestion)
    }

    private fun nextQuestion() {
        if (currentQuestion < questions.size - 1) {
            currentQuestion++
            val numQuestion = currentQuestion + 1
            numQuestionTv?.text = numQuestion.toString()
            startTimer()
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

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerTv?.text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)
            }

            override fun onFinish() {
                nextQuestion()
                setQuestion(currentQuestion)
            }
        }.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = QuestionFragment()
    }
}
