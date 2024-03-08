package com.example.hospitalapplication

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int = 0,
    val name: String = "",
    val password: String = "",
    val email: String = "",
    val phone: String = "",
    val company: String = "",
    val department: String = "",
    val userType: String = "",
    val isDeleted: Boolean = false
)

@Serializable
data class ArtImage(
    val artid: Int = 0,
    val imagesrc: String = "",
    val artimgid: String = "",
)

@Serializable
data class Art(
    val artid: Int = 0,
    val category: Int = 0,
    val name: String = "",
    val description: String = "",
    val artimage: Array<ArtImage>
)

@Serializable
data class Game(
    val gameid: Int = 0,
    val gamesrc: String = "",
    val gameImgSrc: String = "",
    val game_name: String = "",
)

@Serializable
data class Media(
    val mediaid: Int = 0,
    val mediatitle: String = "",
    val mediadescription: String = "",
    val mediasrc: String = "",
    val media_img_src: String = ""
)

@Serializable
data class Patient(
    val patientId: Int = 0,
    val name: String = "",
    val bloodPressureS: String = "",
    val bloodPressureD: String = "",
    val bloodGlucose: Int = 0,
    val bodyTemperature: Double = 0.0,
    val heartRate: Int = 0,
    val itemsToBeInspected: String? = null,
    val medicationOnHand: String = "",
    val recentDiagnosticFindings: String = "",
    val patient_avatar_image: String = "",
)

val handler = CoroutineExceptionHandler { context, throwable ->
    Log.d("LifeScycleScope", "$throwable")
}

class Requests {
    lateinit var supabase: SupabaseClient;
    lateinit var games: List<Game>;
    lateinit var users: User;
    lateinit var patients: Patient;
    lateinit var media: Media;
    lateinit var art: Art;

    init {
        supabase = getClient()
    }

    private fun getClient(): SupabaseClient {
        val supabase = createSupabaseClient(
            supabaseUrl = "https://ntkxrjryfffbtpbtzevx.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im50a3hyanJ5ZmZmYnRwYnR6ZXZ4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDY1OTE1MDAsImV4cCI6MjAyMjE2NzUwMH0.N99GQwoQ4FZg4KhKx9JxZ3qdIFU_gWi-4brpyiV_TJM",
        ) {
            install(Auth)
            install(Postgrest)
        }

        return supabase

    }

    public suspend fun getUsers(): List<User> {
        val supabase = getClient()
        val supabaseResponse = supabase.postgrest.from("user").select(Columns.ALL)
        val data = supabaseResponse.decodeList<User>()
        Log.e("supabase-user", data.toString())
        return data
    }

    public suspend fun getArts(): List<Art> {
        val supabase = getClient()
        val supabaseResponse =
            supabase.postgrest.from("art")
                .select(Columns.raw("artid,name,description,artimage(artid,imagesrc)"))
        val data = supabaseResponse.decodeList<Art>()
        Log.e("supabase", data.toString())
        return data;

    }
    public suspend fun getArtById(artid: String): List<Art> {
        val supabase = getClient()
        val supabaseResponse =
            supabase.postgrest.from("art")
                .select(Columns.raw("artid,name,description,artimage(artid,imagesrc)")){
                    filter {
                        Art::artid eq artid
                        //or
                        eq("artid", artid)
                    }
                }
        val data = supabaseResponse.decodeList<Art>()
        Log.e("supabase", data.toString())
        return data;

    }

    public suspend fun getGames(): List<Game> {
        val supabase = getClient()
        val supabaseResponse = supabase.postgrest.from("game").select(Columns.ALL)
        val data = supabaseResponse.decodeList<Game>()
        games = data
        Log.e("supabase", data.toString())
        return data;

    }

    public suspend fun getMedia(): List<Media> {
        val supabase = getClient()
        val supabaseResponse = supabase.postgrest.from("media").select(Columns.ALL)
        val data = supabaseResponse.decodeList<Media>()
        Log.e("supabase", data.toString())
        return data;

    }

    public suspend fun getPatientInfo(): Patient {
        val supabase = getClient()
        val supabaseResponse = supabase.postgrest.from("patient").select(Columns.ALL) {
            filter {
                eq("patientId", "11")
            }
        }

        val data = supabaseResponse.decodeSingle<Patient>()
        Log.e("supabase", data.toString())
        patients = data
        return data;

    }
}