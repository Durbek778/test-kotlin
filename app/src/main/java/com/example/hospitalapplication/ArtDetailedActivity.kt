package com.example.hospitalapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.hospitalapplication.adapters.ImageAdapter
import com.example.hospitalapplication.databinding.ActivityArtDetailedBinding
import com.example.hospitalapplication.models.ImageItem
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*

class ArtDetailedActivity : AppCompatActivity() {
    private lateinit var viewpager2: ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private lateinit var binding:ActivityArtDetailedBinding
    private lateinit var art: Art
    var imageList =  arrayListOf<ImageItem>(
        ImageItem(
            UUID.randomUUID().toString(),
            URL("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fphotos%2Fplaceholder-image&psig=AOvVaw0E73zrybCavsFng-KCV22-&ust=1709975136057000&source=images&cd=vfe&opi=89978449&ved=0CBMQjRxqFwoTCIjdmPan5IQDFQAAAAAdAAAAABAE")
        ),
        ImageItem(
            UUID.randomUUID().toString(),
            URL("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fphotos%2Fplaceholder-image&psig=AOvVaw0E73zrybCavsFng-KCV22-&ust=1709975136057000&source=images&cd=vfe&opi=89978449&ved=0CBMQjRxqFwoTCIjdmPan5IQDFQAAAAAdAAAAABAE")
        ),
        ImageItem(
            UUID.randomUUID().toString(),
            URL("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fphotos%2Fplaceholder-image&psig=AOvVaw0E73zrybCavsFng-KCV22-&ust=1709975136057000&source=images&cd=vfe&opi=89978449&ved=0CBMQjRxqFwoTCIjdmPan5IQDFQAAAAAdAAAAABAE")
        ),

        )
    private val autoScrollHandler = Handler()
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val currentItem = viewpager2.currentItem
            viewpager2.setCurrentItem((currentItem + 1) % imageList.size, true)
            autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL)
        }
    }

    companion object {
        fun newInstance(context: Context, art: Art): Intent {
            return Intent(context, ArtDetailedActivity::class.java).apply {
                putExtra(ART_EXTRA, art.description)
                putExtra(ART_IMAGE_ID, art.artid.toString())


            }
        }

        private const val ART_EXTRA = "modelArt"
        private const val ART_IMAGE_ID= "imageId"

        const val AUTO_SCROLL_INTERVAL =
            2500L // Auto-scroll interval in milliseconds (adjust as needed)
    }



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

        lifecycleScope.launch {
            val artid = intent.extras?.getString(ART_IMAGE_ID)
             art = supabase.getArtById(artid.toString())[0]
            val imageCount = art.artimage.size
            for (i in 0 until imageList.size.coerceAtMost(imageCount)) {
                    var existingImage = imageList[i]
                    Log.e("IMAGE URL", i.toString())
                    Log.e("IMAGE URL", URL(art.artimage[i].imagesrc).toString())
                    existingImage.url = URL(art.artimage[i].imagesrc)
                    existingImage.url = URL(art.artimage[i].imagesrc)

            }
            viewpager2.adapter?.notifyDataSetChanged()
        }

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