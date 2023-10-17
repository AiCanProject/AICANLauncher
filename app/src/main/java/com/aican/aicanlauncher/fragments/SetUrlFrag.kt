package com.aican.aicanlauncher.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aican.aicanlauncher.R
import com.aican.aicanlauncher.util.Source

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SetUrlFrag : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var urlEdit: EditText
    lateinit var setUrlBtn: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUrlBtn  = view.findViewById(R.id.setUrlBtn)
        urlEdit  = view.findViewById(R.id.urlEdit)

        val preference = requireContext().getSharedPreferences("WEB_URL", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()

        urlEdit.setText(preference.getString("WEB_URL","").toString())

        setUrlBtn.setOnClickListener {
            if (urlEdit.text.toString().trim() == ""){
                urlEdit.error = "Can't be empty"
            }else{
                editor.putString("WEB_URL", urlEdit.text.toString())
                editor.commit()
                if (preference.getString("WEB_URL","") != ""){
                    Toast.makeText(
                        context,
                        "Url set",
                        Toast.LENGTH_LONG
                    )
                        .show()                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_url, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetUrlFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetUrlFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}