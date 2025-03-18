package it.mdp02.tiktokforwarderfortelegram

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences("telegramtok_prefs", MODE_PRIVATE)
        val selectedBot = preferences.getString("selected_bot", "uvd_bot") // Default: "uvd_bot"

        val data: Uri? = intent?.data
        if (data != null && selectedBot != null) {
            forwardToTelegram(data.toString(), selectedBot)
        } else {
            finish() // Chiude l'activity se non ci sono dati validi
        }
    }

    private fun forwardToTelegram(url: String, botName: String) {
        val telegramUri = "https://t.me/$botName?text=${Uri.encode(url)}".toUri()
        val telegramIntent = Intent(Intent.ACTION_VIEW, telegramUri)
        telegramIntent.setPackage("org.telegram.messenger")

        try {
            startActivity(telegramIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Errore nell'aprire Telegram", Toast.LENGTH_SHORT).show()
        }

        finish() // Chiude l'app dopo aver inviato il link
    }
}
