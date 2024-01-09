package com.kelompoktmr.wesata.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.kelompoktmr.wesata.apis.Host
import com.kelompoktmr.wesata.adapters.NotifAdapter
import com.kelompoktmr.wesata.dataclass.NotificationData
import com.kelompoktmr.wesata.databinding.FragmentNotificationsBinding
import org.json.JSONArray


class NotificationsFragment : Fragment() {
    lateinit var binding: FragmentNotificationsBinding
    lateinit var dataNotif: ArrayList<NotificationData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root

        loadData()

        return view
    }

    fun loadData() {
        dataNotif = ArrayList<NotificationData>()

        AndroidNetworking.get("http://${Host.URL.url}/api/notifications")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        //val id = jsonObject.getInt("id")
                        val title = jsonObject.getString("title")
                        val message = jsonObject.getString("message")

                        dataNotif.add(NotificationData(title, message))
                    }
                    binding.rvNotif.layoutManager = LinearLayoutManager(activity)
                    binding.rvNotif.adapter = NotifAdapter(dataNotif)
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })


    }

}