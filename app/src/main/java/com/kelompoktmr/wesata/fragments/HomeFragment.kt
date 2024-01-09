package com.kelompoktmr.wesata.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.kelompoktmr.wesata.apis.Host
import com.kelompoktmr.wesata.adapters.WisataAdapter
import com.kelompoktmr.wesata.dataclass.WisataData
import com.kelompoktmr.wesata.databinding.FragmentHomeBinding
import org.json.JSONArray


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var dataWisata: ArrayList<WisataData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        loadData()
        return view
    }

    fun loadData() {
        dataWisata = ArrayList<WisataData> ()

        AndroidNetworking.get("http://${Host.URL.url}/api/destinations")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val id = jsonObject.getInt("id")
                        val name = jsonObject.getString("name")
                        val desc = jsonObject.getString("desc")
                        val img = jsonObject.getString("img")
                        dataWisata.add(WisataData(id, name, desc, img))

                    }
                    binding.rvWisata.layoutManager = LinearLayoutManager(activity)
                    binding.rvWisata.adapter = WisataAdapter(dataWisata)
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })



    }
}