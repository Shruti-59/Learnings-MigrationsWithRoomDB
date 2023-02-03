package com.example.demoviewmodel.data

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesappwithroom.model.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
     suspend fun delete(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT *FROM note_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String) : LiveData<List<Note>>



}