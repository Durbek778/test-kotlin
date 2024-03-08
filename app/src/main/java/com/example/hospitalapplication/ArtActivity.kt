package com.example.hospitalapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hospitalapplication.adapters.ArtAdapter
import com.example.hospitalapplication.databinding.ActivityArtBinding
import kotlinx.coroutines.launch

class ArtActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArtBinding
    private lateinit var rcAdapter:ArtAdapter
     lateinit var arts:List<Art>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        lifecycleScope.launch {
             arts = supabase.getArts()
             rcAdapter=ArtAdapter(arts) { art: Art ->
                startActivity(ArtDetailedActivity.newInstance(this@ArtActivity,art))
            }
            binding.recyclerArt.adapter =rcAdapter

        }
/*

        binding.navigationToArtDatailedd?.setOnClickListener {
            val Intent = Intent(this, ArtDetailedActivity::class.java)
            startActivity(Intent)

        }
        binding.navigationToArtDatailedd?.setOnClickListener {
            val Intent = Intent(this, ArtDetailedActivity::class.java)
            startActivity(Intent)
        }
        binding.navigationToArtDataileddd?.setOnClickListener {
            val Intent = Intent(this, ArtDetailedActivity::class.java)
            startActivity(Intent)
        }*/
    }
}