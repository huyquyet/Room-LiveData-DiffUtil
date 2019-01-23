package com.phoneappli.room_livedata_diffutil


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections.emptyList
import java.util.concurrent.Executors


class WordListAdapter constructor(
    context: Context
) : ListAdapter<Word, WordListAdapter.ViewHolder>(
    AsyncDifferConfig.Builder<Word>(NoteDiffCallBack())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
        val id: TextView = itemView.findViewById(R.id.id)

        fun onBind(word: Word) {
            wordItemView.text = word.word
            id.text = word.id.toString()
        }
    }

    override fun submitList(list: List<Word>?) {
        super.submitList(ArrayList<Word>(list ?: listOf()))
    }
}


