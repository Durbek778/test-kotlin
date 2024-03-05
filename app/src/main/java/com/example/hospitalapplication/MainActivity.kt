package com.example.hospitalapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hospitalapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

lateinit var supabase: Requests


class MainActivity : AppCompatActivity() {
    lateinit var pulse_text: TextView
    lateinit var pills_text: TextView
    lateinit var infusion_text: TextView
    lateinit var profile_image: ImageView
    private lateinit var binding: ActivityMainBinding
    var utils = Utils()
    suspend fun initSupabase() {
        supabase = Requests()
    }

    fun initUI() {
        profile_image = findViewById(R.id.profile_image)
        pulse_text = findViewById(R.id.pulse_text)
        infusion_text = findViewById(R.id.infusion_text)
        pills_text = findViewById(R.id.pills_text)
    }

    fun updatePatientInfo() {
        utils.getImageBitmap(supabase.patients.patient_avatar_image) { bitmap ->
            profile_image.setImageBitmap(bitmap as Bitmap?)
        }
        pulse_text.text = "체온:" + supabase.patients.heartRate.toString() + "bpm";
        pills_text.text =
            "혈압:" + supabase.patients.bloodPressureS + "/" + supabase.patients.bloodPressureD;
        infusion_text.text = "혈당:" + supabase.patients.bloodGlucose.toString() + "";

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

        lifecycleScope.launch(handler) {
            initSupabase()
            supabase.getPatientInfo()
            updatePatientInfo()

        }

        supportActionBar?.hide()

        binding.navigationToProfileEditAcv?.setOnClickListener {
            val Intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(Intent)

        }

    //   val navigationGame = findViewById<LinearLayout>(R.id.navigation_to_game)
        binding.navigationToGame.setOnClickListener {
            val Intent = Intent(this, GameActivity::class.java)
            startActivity(Intent)

        }

     //   val navigationArt = findViewById<LinearLayout>(R.id.navigation_to_artAct)
        binding.navigationToArtAct.setOnClickListener {
            val Intent = Intent(this, ArtActivity::class.java)
            startActivity(Intent)

        }

      //  val navigationMedia = findViewById<LinearLayout>(R.id.navigation_to_mediaAct)
        binding.navigationToMediaAct.setOnClickListener {
            val Intent = Intent(this, MediaActivity::class.java)
            startActivity(Intent)

        }
    }


}