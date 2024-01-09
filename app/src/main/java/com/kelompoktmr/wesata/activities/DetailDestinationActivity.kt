package com.kelompoktmr.wesata.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.kelompoktmr.wesata.apis.Host
import com.kelompoktmr.wesata.databinding.ActivityDetailDestinationBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject


class DetailDestinationActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailDestinationBinding
    lateinit var webUrl: String
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadData()

        binding.btnDetailDestinationMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
            intent.putExtra("title", title)
            startActivity(intent)
        }

        binding.btnDetailDestinationWeb.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            intent.putExtra("url", webUrl)
            startActivity(intent)
        }
    }

    private fun loadData() {
        val idWisata = intent.getStringExtra("idWisata").toString()
        AndroidNetworking.get("http://${Host.URL.url}/api/destinations/$idWisata")

            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    title = response.getString("name")
                    webUrl = response.getString("web_url")
                    latitude = response.getString("latitude")
                    longitude = response.getString("longitude")
                    binding.tvDetailDestinationTitle.text = response.getString("name")
                    binding.tvDetailDestinationDescription.text = response.getString("desc")
                    Picasso.get()
                        .load(response.getString("img"))
                        .into(binding.ivDetailDestination)
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })
    }

}