package com.example.tp01_leo_hotz

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.tp01_leo_hotz.datas.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentResult : Fragment() {
    private var backButtonBtn: Button? = null
    private var resultScoreTv: TextView? = null
    private var score: String? = "0"
    private var theme: String? = "Default"
    private var playerNameTv: TextView? = null
    private var bestScoreTv: TextView? = null
    private var playerName: String? = "Player"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        val arguments = arguments
        theme = arguments?.getString("theme")
        score = arguments?.getString("score")

        backButtonBtn = view.findViewById(R.id.backButtonBtn)
        resultScoreTv = view.findViewById(R.id.resultScoreTv)
        playerNameTv = view.findViewById(R.id.playerNameTv)
        bestScoreTv = view.findViewById(R.id.bestResultScoreTv)

        backButtonBtn?.setOnClickListener {
            backToMenu()
        }

        getPlayerName(requireContext())
        setScore()
        getBestScore()

        return view
    }

    private fun backToMenu() {
        val menu = ListeQuizz()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentView, menu)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getBestScore() {
        CoroutineScope(Dispatchers.IO).launch {
            val bestScore = MyApp.database.scoreDao().getBestScoreByCategory(theme.toString())
            withContext(Dispatchers.Main) {
                bestScoreTv?.text = if (bestScore != null) {
                    "${bestScore.score} (${bestScore.playerName})"
                } else {
                    "No scores yet"
                }
            }
        }
    }

    private fun setScore() {
        resultScoreTv?.text = score

        val myScore = Score(
            playerName = playerName ?: "Player",
            score = score?.toInt() ?: 0,
            category = theme ?: ""
        )

        CoroutineScope(Dispatchers.IO).launch {
            MyApp.database.scoreDao().insert(myScore)
            getBestScore()  // Refresh best score after inserting the new score
        }
    }

    private fun getPlayerName(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("PlayerPrefs", Context.MODE_PRIVATE)
        playerName = sharedPreferences.getString("playerName", "Player")
        playerNameTv?.text = playerName
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentResult().apply {
                arguments = Bundle().apply {
                    putString("theme", param1)
                    putString("score", param2)
                }
            }
    }
}
