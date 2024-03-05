package com.example.hospitalapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hospitalapplication.adapters.MediaAdapter
import com.example.hospitalapplication.databinding.ActivityMainBinding
import com.example.hospitalapplication.databinding.ActivityMediaBinding
import kotlinx.coroutines.launch

class MediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMediaBinding
    private lateinit var mediaADapter: MediaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        lifecycleScope.launch {
            val media = supabase.getMedia()
            mediaADapter = MediaAdapter(media) { media: Media ->
                Log.d("TAG", "onCreate:" + media.mediasrc )
                openVideoUrl(media.mediasrc)
            }
            binding.recyclerMediarc.adapter = mediaADapter
        }


    }

    private fun playVideo(resourceId: Int) {
        val intent = Intent(this, WatchActivity::class.java)
        intent.putExtra(WatchActivity.EXTRA_VIDEO_RESOURCE_ID, resourceId)
        startActivity(intent)
    }
    private fun openVideoUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            // Uncomment the following line if you specifically want to open the URL in a web browser
            // category = Intent.CATEGORY_BROWSABLE
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d("TAG", "No Intent available to handle action")
        }
    }
}
