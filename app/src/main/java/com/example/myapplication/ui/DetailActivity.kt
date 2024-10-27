package com.example.myapplication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.local.entity.EventsEntity
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.utils.ViewModelFactory


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var binding: ActivityDetailBinding

    private var isFavorite = false

    // menghubungkan viewmodel
    private lateinit var detailViewModel: DetailViewModel

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

        detailViewModel = obtainViewModel(this@DetailActivity)




        // elsplisit intent untuk detail
        val event = if (Build.VERSION.SDK_INT <= 30) {
            intent.getParcelableExtra(KEY_EVENT, ListEventsItem::class.java)
        } else {
            intent.getParcelableExtra(KEY_EVENT)
        }

        if (event != null) {
            // Cek apakah event sudah ada di database dan setel status isFavorite
            detailViewModel.isEventFavorite(event.id.toString()).observe(this) { isFavoriteInDb ->
                isFavorite = isFavoriteInDb
                updateFavoriteIcon()
            }


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

        // fab untuk insert dan delete dari favorite
        binding.fabFavorite.setOnClickListener {
            val listEvent = if (Build.VERSION.SDK_INT <= 30) {
                intent.getParcelableExtra(KEY_EVENT, ListEventsItem::class.java)
            } else {
                intent.getParcelableExtra(KEY_EVENT)
            }

            if (listEvent != null){
                val eventEntity = EventsEntity(
                    listEvent.id.toString(),
                    listEvent.name,
                    listEvent.mediaCover,
                    listEvent.description,
                    listEvent.beginTime,
                    listEvent.endTime,
                    listEvent.ownerName,
                    listEvent.summary,
                    listEvent.category,
                    listEvent.cityName,
                    listEvent.quota,
                    listEvent.registrants,
                    listEvent.link,
                    listEvent.imageLogo
                )
                if (isFavorite) {
                    detailViewModel.delete(eventEntity)
                } else {
                    detailViewModel.insert(eventEntity)
                }
                isFavorite = !isFavorite
                updateFavoriteIcon()
            }else {
                Toast.makeText(this, "Event tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    private fun updateFavoriteIcon() {
        // Update FAB icon berdasarkan status favorit
        val drawable = if (isFavorite) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_24
        }
        binding.fabFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                binding.fabFavorite.context,
                drawable
            )
        )
    }

    // onClick button untuk implicit item
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_link -> {
                // getparcelable extra yang ada di liseventitem untuk mendapatkan link tujuan
                val link = if (Build.VERSION.SDK_INT <= 30) {
                    intent.getParcelableExtra(KEY_EVENT, ListEventsItem::class.java)
                } else {
                    intent.getParcelableExtra(KEY_EVENT)
                }
                if (link != null) {
                    val web = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(link.link)
                    ) // setelah mendapatkan parcelable, panggil link yanag ada di listeventitem untuk dijadikan link tujuan.
                    startActivity(web)
                }
            }
        }
    }
}