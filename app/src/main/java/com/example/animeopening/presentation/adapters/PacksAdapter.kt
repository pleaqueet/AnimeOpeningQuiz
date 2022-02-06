package com.example.animeopening.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animeopening.R
import com.example.animeopening.domain.models.Opening
import java.io.File

class PacksAdapter(
    private val context: Context,
    private val packs: List<String>,
    private val openings: List<Opening>,
    private val packClickListener: PackClickListener
) : RecyclerView.Adapter<PacksAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val packTextView: TextView = itemView.findViewById(R.id.pack_text_view)
        val statusTextView: TextView = itemView.findViewById(R.id.status_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.pack_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.packTextView.text = packs[position]
        holder.itemView.setOnClickListener {
            packClickListener.onPackClickListener(position)
        }
        if (position == 0) {
            if (File(context.filesDir, openings[position+5].mp3).exists()) {
                holder.statusTextView.text = context.resources.getText(R.string.play)
            } else {
                holder.statusTextView.text = context.resources.getText(R.string.download)
            }}
//        } else {
//            if (File(context.filesDir, openings[position*10+5].mp3).exists()) {
//                holder.statusTextView.text = context.resources.getText(R.string.play)
//            } else {
//                holder.statusTextView.text = context.resources.getText(R.string.download)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return packs.size
    }

    interface PackClickListener {
        fun onPackClickListener(position: Int)
    }
}