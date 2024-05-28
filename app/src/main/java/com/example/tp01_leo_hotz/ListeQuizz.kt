package com.example.tp01_leo_hotz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.tp01_leo_hotz.enums.Categories

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListeQuizz.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListeQuizz : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_liste_quizz, container, false)
        val cimemaBtn: Button = view.findViewById(R.id.cinemaBtn)
        cimemaBtn.setOnClickListener {
            startGame(Categories.CINEMA);
        }
        val jeuxVideoBtn: Button = view.findViewById(R.id.jeuxVideoBtn)
        jeuxVideoBtn.setOnClickListener {
            startGame(Categories.JEUX_VIDEOS);
        }
        val musiqueBtn: Button = view.findViewById(R.id.musiqueBtn)
        musiqueBtn.setOnClickListener {
            startGame(Categories.MUSIQUE);
        }


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListeQuizz.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ListeQuizz()
    }

    private fun startGame(theme: Categories) {
        val questionFragment = QuestionFragment()

        val bundle = Bundle()
        bundle.putString("theme", theme.toString())
        questionFragment.arguments = bundle

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentView, questionFragment)
        transaction.addToBackStack(null) // this allows you to go back to the previous fragment
        transaction.commit()
    }

}