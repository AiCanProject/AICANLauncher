package com.aican.aicanlauncher.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aican.aicanlauncher.DashboardSettings
import com.aican.aicanlauncher.R
import com.aican.aicanlauncher.settingLock.LockScreen
import com.aican.aicanlauncher.util.Source

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetIDPasswordFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetIDPasswordFrag : Fragment() {
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

    lateinit var etID: EditText
    lateinit var etPassword: EditText
    lateinit var confirmIdPass: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etID = view.findViewById(R.id.etID)
        etPassword = view.findViewById(R.id.etPassword)
        confirmIdPass = view.findViewById(R.id.confirmIDPass)



        val sharedPreference = requireContext().getSharedPreferences(
            Source.ID_PASSWORD,
            AppCompatActivity.MODE_PRIVATE
        )


        val editor = sharedPreference.edit()


        confirmIdPass.setOnClickListener {
            if (etID.text.toString() == "" || etPassword.text.toString() == "") {
                if (etID.text.toString() == "") {
                    etID.error = "Enter your ID"
                }
                if (etPassword.text.toString() == "") {
                    etPassword.error = "Enter your Password"
                }
            } else {
                editor.putString(Source.ID, etID.text.toString())
                editor.putString(Source.PASSWORD, etPassword.text.toString())
                editor.commit()
                val id: String = sharedPreference.getString(Source.ID, "").toString()
                if (id != "") {
                    Toast.makeText(
                        context,
                        "ID password changed, remember ID password to unlock settings",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_id_password, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetIDPasswordFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetIDPasswordFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}