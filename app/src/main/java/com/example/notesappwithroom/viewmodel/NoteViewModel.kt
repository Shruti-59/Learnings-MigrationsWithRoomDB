package com.example.demoviewmodel.viewmodel

import android.app.Application
import androidx.lifecycle.*

import com.example.demoviewmodel.data.NoteDatabase

import com.example.notesappwithroom.model.Note
import com.example.notesappwithroom.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel (application: Application): AndroidViewModel(application) {

      val getAllNotes :LiveData<List<Note>>
    private val notesRepository : NotesRepository

    init{
        val noteDao = NoteDatabase.getInstance(application).noteDao()
        notesRepository = NotesRepository(noteDao!!)
        getAllNotes = notesRepository.getAllNotes
    }

    fun addNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.addNote(note)
        }
    }

    fun updateNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNote(note)
        }
    }

    fun deleteNotes(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteNote(note)
        }
    }

    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.deleteAllNotes()
        }
    }

    fun searchDatabase(searchQuery : String): LiveData<List<Note>> {
        return notesRepository.searchDatabase(searchQuery)
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}