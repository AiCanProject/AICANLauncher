package com.aican.aicanlauncher.fragments

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.aican.aicanlauncher.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResetLauncherFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResetLauncherFrag : Fragment() {
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

    lateinit var homescreen: ImageView
    lateinit var resetLauncher: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homescreen = view.findViewById(R.id.homescreen)
        resetLauncher = view.findViewById(R.id.resetLauncher)

//        Glide.with(this).load(resources.getDrawable(R.drawable.homescreen)).into(homescreen)
        resetLauncher.setOnClickListener {
            val pm = requireContext().packageManager
            val cn1 =
                ComponentName("com.aican.aicanlauncher", "com.aican.aicanlauncher.LauncherAlias1")
            val cn2 =
                ComponentName("com.aican.aicanlauncher", "com.aican.aicanlauncher.LauncherAlias2")
            var dis = PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            if (pm.getComponentEnabledSetting(cn1) == dis) dis = 3 - dis
            pm.setComponentEnabledSetting(cn1, dis, PackageManager.DONT_KILL_APP)
            pm.setComponentEnabledSetting(cn2, 3 - dis, PackageManager.DONT_KILL_APP)

            val startMain = Intent(Intent.ACTION_MAIN)
            startMain.addCategory(Intent.CATEGORY_HOME)
            startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(startMain)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_launcher, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResetLauncherFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResetLauncherFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}