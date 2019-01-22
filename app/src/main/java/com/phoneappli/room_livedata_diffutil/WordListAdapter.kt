package com.phoneappli.room_livedata_diffutil

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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


//class WordListAdapter1 internal constructor(
//    context: Context
//) : RecyclerView.Adapter<WordListAdapter1.WordViewHolder>() {
//
//    private val inflater: LayoutInflater = LayoutInflater.from(context)
//    private var words = emptyList<Word>() // Cached copy of words
//
//    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val wordItemView: TextView = itemView.findViewById(R.id.textView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
//        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
//        return WordViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
//        val current = words[position]
//        holder.wordItemView.text = current.word
//    }
//
//    internal fun setWords(words: List<Word>) {
//        this.words = words
//        Log.i("---------> : ", "WordListAdapter : setWords: ")
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount() = words.size
//}

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
        val current = getItem(position)
        holder.wordItemView.text = current.word
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun submitList(list: List<Word>?) {
        super.submitList(ArrayList<Word>(list ?: listOf()))
    }
}


