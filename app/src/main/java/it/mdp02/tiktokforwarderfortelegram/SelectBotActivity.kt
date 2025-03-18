package it.mdp02.tiktokforwarderfortelegram

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SelectBotActivity : AppCompatActivity() {

    private val bots = listOf("uvd_bot", "DownloadsMasterBot") // Aggiungi qui i tuoi bot
    private lateinit var botAdapter: BotAdapter
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_bot)

        preferences = getSharedPreferences("telegramtok_prefs", MODE_PRIVATE)

        val disclaimerSeen = preferences.getBoolean("disclaimer_seen", false)
        if (!disclaimerSeen) {
            showDisclaimerDialog(preferences)
        }

        val currentBotName: TextView = findViewById(R.id.currentBotName)
        currentBotName.text = getString(R.string.current_bot_name, preferences.getString("selected_bot", "uvd_bot"))

        val recyclerView: RecyclerView = findViewById(R.id.botRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        botAdapter = BotAdapter(bots) { botName ->
            // Salva la scelta del bot
            preferences.edit {
                putString("selected_bot", botName)
                apply()
            }
            Toast.makeText(this, "Bot selezionato: $botName", Toast.LENGTH_SHORT).show()
            currentBotName.text = getString(R.string.current_bot_name, preferences.getString("selected_bot", "null"))
        }

        recyclerView.adapter = botAdapter
    }

    private fun showDisclaimerDialog(sharedPreferences: SharedPreferences) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.disclaimer_title))
            .setMessage(getString(R.string.disclaimer_message))
            .setPositiveButton(getString(R.string.disclaimer_confirm)) { dialog, _ ->
                // Salva che il disclaimer è stato visto
                sharedPreferences.edit() { putBoolean("disclaimer_seen", true) }
                dialog.dismiss()
            }
            .setCancelable(false)  // Non può essere annullato premendo fuori
            .show()
    }
}