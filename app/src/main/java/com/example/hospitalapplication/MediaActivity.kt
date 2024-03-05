package com.example.hospitalapplication

import android.content.Intent
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
                Log.d("TAG", "onCreate:" + media.media_img_src )
            }
            binding.recyclerMediarc.adapter = mediaADapter
        }

        /*        val bt_video1 = findViewById<LinearLayout>(R.id.bt_video1)
                val bt_video2 = findViewById<LinearLayout>(R.id.bt_video2)
                val bt_video3 = findViewById<LinearLayout>(R.id.bt_video3)
        //        val bt_video4 = findViewById<LinearLayout>(R.id.bt_video4)

                bt_video1.setOnClickListener { playVideo(R.raw.videoplayback) }
                bt_video2.setOnClickListener { playVideo(R.raw.videoplayback) }
                bt_video3.setOnClickListener { playVideo(R.raw.videoplayback) }
        //        bt_video4.setOnClickListener { playVideo(R.raw.video_napaleon) }*/
    }

    private fun playVideo(resourceId: Int) {
        val intent = Intent(this, WatchActivity::class.java)
        intent.putExtra(WatchActivity.EXTRA_VIDEO_RESOURCE_ID, resourceId)
        startActivity(intent)
    }
}
