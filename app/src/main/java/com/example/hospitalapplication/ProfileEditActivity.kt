package com.example.hospitalapplication

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class ProfileEditActivity : AppCompatActivity() {
    lateinit var profile_image: ImageView
    lateinit var patient_name: TextView
    lateinit var heart_rate: TextView
    lateinit var blood_pressure: TextView
    lateinit var items_inspected: TextView
    lateinit var madication_onhand: TextView
    lateinit var diagnostic_findings: TextView
    var utils = Utils()


    fun initUI() {
        profile_image = findViewById(R.id.profile_image)
        patient_name = findViewById(R.id.patient_name)
        heart_rate = findViewById(R.id.heard_rate)
        blood_pressure = findViewById(R.id.blood_pressure)
        items_inspected = findViewById(R.id.items_inspected)
        madication_onhand = findViewById(R.id.madication_onhand)
        diagnostic_findings = findViewById(R.id.diagnostic_findings)

    }


    fun updatePatientInfo() {
        utils.getImageBitmap(supabase.patients.patient_avatar_image) { bitmap ->
            profile_image.setImageBitmap(bitmap as Bitmap?)
        }
        patient_name.text = supabase.patients.name
        heart_rate.text = supabase.patients.heartRate.toString() + " bpm"
        blood_pressure.text =
            supabase.patients.bloodPressureS + "/" + supabase.patients.bloodPressureD
        items_inspected.text = supabase.patients.itemsToBeInspected
        madication_onhand.text = supabase.patients.medicationOnHand
        diagnostic_findings.text = supabase.patients.recentDiagnosticFindings
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        supportActionBar?.hide()


        lifecycleScope.launch {
            supabase.getPatientInfo()
            initUI()
            updatePatientInfo()
        }
    }
}