package com.example.hospitalapplication

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.hospitalapplication.adapters.ImageAdapter
import com.example.hospitalapplication.databinding.ActivityArtDetailedBinding
import com.example.hospitalapplication.models.ImageItem
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class ArtDetailedActivity : AppCompatActivity() {
    private lateinit var viewpager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private lateinit var binding:ActivityArtDetailedBinding

    private val autoScrollHandler = Handler()
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val currentItem = viewpager2.currentItem
            viewpager2.setCurrentItem((currentItem + 1) % imageList.size, true)
            autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL)
        }
    }

    companion object {
        fun newInstance(context: Context, text:String): Intent {
            return Intent(context, ArtDetailedActivity::class.java).apply {
                putExtra(ART_EXTRA, text)
            }
        }

        private const val ART_EXTRA = "modelArt"
        const val AUTO_SCROLL_INTERVAL =
            2500L // Auto-scroll interval in milliseconds (adjust as needed)
    }


    val imageList =
        arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.korean_art
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.korean_art
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                R.drawable.korean_art
            ),

            )

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    private fun startAutoScroll() {
        autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL)
    }

    private fun stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8, 0, 8, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityArtDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val description = intent.extras?.getString(ART_EXTRA)
        supportActionBar?.hide()

        viewpager2 = binding.viewpager2

        val scrollView = binding.svWrapper
        val textView = binding.tvLorem // Replace with your TextView's id

        // Make the TextView scrollable
        textView.movementMethod = ScrollingMovementMethod.getInstance()
        textView.isVerticalScrollBarEnabled = true

        // Make the ScrollView scrollable
        scrollView.isVerticalScrollBarEnabled = true

        textView.setText(description)

        val imageAdapter = ImageAdapter()
        viewpager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        viewpager2.setPageTransformer { page, position ->
            val absPosition = Math.abs(position)
            page.alpha = 1f - absPosition
            page.scaleY = 0.85f + 0.15f * (1f - absPosition)
            page.translationX =
                -position * page.width / 4  // Adjust the speed by changing the denominator
        }

        val slideDotLL = binding.slideDotLL
        val dotsImage = Array(imageList.size) { ImageView(this) }

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            slideDotLL?.addView(it, params)
        }

        // default first dot selected
        dotsImage[0].setImageResource(R.drawable.active_dot)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) {
                        imageView.setImageResource(
                            R.drawable.active_dot
                        )
                    } else {
                        imageView.setImageResource(R.drawable.non_active_dot)
                    }
                }
                super.onPageSelected(position)
            }
        }
        viewpager2.registerOnPageChangeCallback(pageChangeListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewpager2.unregisterOnPageChangeCallback(pageChangeListener)
    }


}