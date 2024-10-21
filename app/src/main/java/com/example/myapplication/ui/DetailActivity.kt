package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.databinding.ActivityDetailBinding


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val KEY_EVENT = "key_event"
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        // elsplisit intent untuk detail
        val event = if (Build.VERSION.SDK_INT <= 30) {
            intent.getParcelableExtra(KEY_EVENT, ListEventsItem::class.java)
        } else {
            intent.getParcelableExtra(KEY_EVENT)
        }

        if (event != null){
            // Display event data
            binding.name.text = event.name
            binding.description.text = HtmlCompat.fromHtml(
                event.description,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            binding.date.text = "Begin Time : ${event.beginTime}\nEnd Time : ${event.endTime}"
            binding.ownerName.text = event.ownerName
            binding.summary.text = event.summary
            binding.category.text = event.category
            binding.cityName.text = event.cityName
            binding.quota.text = "Sisa Quota : ${event.quota - event.registrants}  "
            binding.registrant.text = "Registrant ${event.registrants}"

            // Load image menggunakan Glide
            Glide.with(this)
                .load(event.mediaCover)
                .fitCenter()
                .into(binding.mediaCover)
        } else {
            // jika event null
            binding.name.text = "Event data not available"
        }

        // implicit intent untuk button
        binding.btnLink.setOnClickListener(this)
    }

    // onClick button untuk implicit item
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onClick(p0: View?) {
        // getparcelable extra yang ada di liseventitem untuk mendapatkan link tujuan
        val link = if (Build.VERSION.SDK_INT <= 30) {
            intent.getParcelableExtra(KEY_EVENT, ListEventsItem::class.java)
        } else {
            intent.getParcelableExtra(KEY_EVENT)
        }
        if (link != null){
            val web = Intent(Intent.ACTION_VIEW, Uri.parse(link.link)) // setelah mendapatkan parcelable, panggil link yanag ada di listeventitem untuk dijadikan link tujuan.
            startActivity(web)
        }

    }


}