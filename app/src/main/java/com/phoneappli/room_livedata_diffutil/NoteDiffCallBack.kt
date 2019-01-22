package com.phoneappli.room_livedata_diffutil

import androidx.recyclerview.widget.DiffUtil

class NoteDiffCallBack : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.word == newItem.word
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}