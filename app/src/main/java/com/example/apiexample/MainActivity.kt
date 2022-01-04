package com.example.apiexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        val requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        val search = findViewById<Button>(R.id.search)
        val userinput = findViewById<EditText>(R.id.userinput)
        val name = findViewById<TextView>(R.id.name)
        val plot = findViewById<TextView>(R.id.plot)
        val image = findViewById<ImageView>(R.id.image)

        fun fetchData( input: String){
            val url = "http://www.omdbapi.com/?i=tt3896198&apikey=ad629747"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    if(response.get("Response")=="False"){
                        name.text = "Incorrect detail"
                    }else {
                        Glide.with(this).load(response.getString("Poster")).into(image)
                        plot.text = response.getString("Plot")
                        name.text = response.getString("Title")+"\n\n"+"Writer: "+response.getString("Writer")
                    }
                },
                { error ->
                    Log.d("vol",error.toString())
                }
            )

            requestQueue.add(jsonObjectRequest)
        }

        search.setOnClickListener {
            var input = userinput.text.toString()
            fetchData(input)
        }


    }
}