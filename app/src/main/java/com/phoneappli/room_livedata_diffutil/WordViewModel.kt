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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.phoneappli.room_livedata_diffutil.Word
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.CoroutineContext

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: kotlin.coroutines.experimental.CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: WordRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    var allWords: LiveData<List<Word>>
//    var allWords: MutableLiveData<List<Word>> = MutableLiveData()

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, scope).wordDao()
        repository = WordRepository(wordsDao)
//        allWords = repository.allWords
        //
//        val buffer = repository.allWords.value!!
//        for (w: Word in buffer) {
//            w.word = w.word + "s"
//        }
//
//        allWords.value = buffer

        allWords = Transformations.switchMap(repository.allWords) {
            w -> calculateWord(w)
        }


//        allWords = repository.allWords
//        print("vao day 1------")
//        val alWordObs : MediatorLiveData<List<Word>>? = MediatorLiveData()
//        alWordObs?.addSource(repository.allWords) { word ->
//            run {
//                print("vao day----------")
//                calculate(word)
//            }
//        }

//        allWords = repository.allWords
//        repository.allWords.observe(application, Observer {
//
//            words -> words.let { allWords.value = words }
//        })

//        scope.launch(Dispatchers.Main) {
//            repository.allWords.observeForever(application, Observer { words ->
//                // Update the cached copy of the words in the adapter.
//                words?.let { calculateWord(words) }
//            })
//        }
//        repository.allWords.observeForever{ words ->
//                words?.let { calculateWord(words) }
//            }

    }


//    private  fun calculate(word: List<Word>?) {
//        print("vao day")
//        if (word != null) {
//            for (w : Word in word){
//                w.word = w.word + "s"
//            }
//        }
//        allWords.value = word
//    }

    private  fun calculateWord(word: List<Word>): LiveData<List<Word>>? {
      val data: MutableLiveData<List<Word>> = MutableLiveData()
        for (w : Word in word){
            w.word.get(0)
            w.word = w.word
        }
        data.value = word

        return data
    }

    /* fun getWords(): MutableLiveData<List<Word>>? {
         return allWords
     }*/
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = scope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
