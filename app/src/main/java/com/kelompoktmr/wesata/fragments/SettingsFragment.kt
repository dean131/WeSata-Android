package com.kelompoktmr.wesata.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.kelompoktmr.wesata.activities.EmailActivity
import com.kelompoktmr.wesata.activities.SMSActivity
import com.kelompoktmr.wesata.R
import com.kelompoktmr.wesata.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.cvContactAdmin.setOnClickListener {
            val popupMenu = PopupMenu(context, binding.cvContactAdmin)
            popupMenu.inflate(R.menu.contact_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.email -> {
                        val intent = Intent(activity, EmailActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.sms -> {
                        val intent = Intent(activity, SMSActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.call -> {
                        val tel_number = "085720858102"
                        val intentCall = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel_number))
                        startActivity(intentCall)
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()
        }

        return view
    }
}