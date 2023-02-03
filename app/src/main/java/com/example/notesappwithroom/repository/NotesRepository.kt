package com.example.notesappwithroom.repository

import androidx.lifecycle.LiveData
import com.example.demoviewmodel.data.NoteDao
import com.example.notesappwithroom.model.Note
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val noteDao: NoteDao) {

    val  getAllNotes : LiveData<List<Note>> = noteDao.getAllNotes()

   suspend fun addNote(note: Note){
        noteDao.insert(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.update(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.delete(note)
    }

    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }

    fun searchDatabase(searchQuery : String): LiveData<List<Note>> {
        return noteDao.searchDatabase(searchQuery)
    }
}