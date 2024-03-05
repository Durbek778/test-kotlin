package com.example.hospitalapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hospitalapplication.adapters.GameAdapter
import com.example.hospitalapplication.databinding.ActivityGameBinding
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var rcAdapter: GameAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        lifecycleScope.launch {
            val games = supabase.getGames()
            rcAdapter = GameAdapter(games) { game: Game ->
                openUrlInBrowser(game.gamesrc)
            }
            binding.gameRecycler.adapter = rcAdapter
            Log.d("MY_TAG", "onCreate: " + games.get(1).game_name)
        }


        /*
                val subwaysurfers = findViewById<ImageView>(R.id.subwaysurfers)
                subwaysurfers.setOnClickListener {
                    val url = "https://poki.com/kr/g/subway-surfers"

                }

                val colorSort = findViewById<ImageView>(R.id.colorSort)
                colorSort.setOnClickListener {
                    val url = "https://poki.com/en/g/water-color-sort"
                    openUrlInBrowser(url)
                }

                val onetMaster = findViewById<ImageView>(R.id.onetMaster)
                onetMaster.setOnClickListener {
                    val url = "https://poki.com/en/g/onet-master"
                    openUrlInBrowser(url)
                }*/

    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
