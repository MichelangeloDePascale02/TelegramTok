package it.mdp02.tiktokforwarderfortelegram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BotAdapter(private val bots: List<String>, private val onBotClick: (String) -> Unit) : RecyclerView.Adapter<BotAdapter.BotViewHolder>() {

    inner class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val botNameTextView: TextView = itemView.findViewById(R.id.botName)

        init {
            itemView.setOnClickListener {
                onBotClick(bots[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_bot, parent, false)
        return BotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BotViewHolder, position: Int) {
        holder.botNameTextView.text = bots[position]
    }

    override fun getItemCount(): Int {
        return bots.size
    }
}
