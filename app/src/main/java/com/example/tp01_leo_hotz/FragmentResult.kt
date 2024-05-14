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

class FragmentResult : Fragment() {
    var backButtonBtn : Button? = null
    var resultScoreTv: TextView? = null
    var score: String?= "0"
    var theme: String?= "Default"
    var playerNameTv: TextView? = null

    // TODO: Rename and change types of parameters
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arguments = arguments
        theme = arguments?.getString("theme")
        score = arguments?.getString("score")

        val view = inflater.inflate(R.layout.fragment_result, container, false)
        backButtonBtn = view.findViewById(R.id.backButtonBtn)
        backButtonBtn?.setOnClickListener{
            backToMenu()
        }

        resultScoreTv = view.findViewById(R.id.resultScoreTv)
        playerNameTv = view.findViewById(R.id.playerNameTv)
        setScore()
        getPlayerName(requireContext());

        return view
    }


    fun backToMenu(){
        val menu = ListeQuizz()


        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentView, menu)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun setScore(){
        resultScoreTv?.text = score
    }

    fun getPlayerName(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("PlayerPrefs", Context.MODE_PRIVATE)

        val playerName = sharedPreferences.getString("playerName", null)
        playerNameTv?.text = playerName
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentResult().apply {

            }
    }
}