package com.example.demoviewmodel.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notesappwithroom.model.Note
import com.example.notesappwithroom.util.Converters

@Database(entities = [Note::class], version = 5, exportSchema = true,
    autoMigrations = [AutoMigration (from = 4, to = 5 )])
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao?

    companion object {
      /*  private var instance: NoteDatabase? = null
        @Synchronized
        fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java, "note_database"
                )
                    .fallbackToDestructiveMigration()

                    .build()
            }
            return instance!!
        }*/


        private lateinit var INSTANCE: NoteDatabase

        fun getInstance(context: Context): NoteDatabase {
            synchronized(NoteDatabase::class.java) {
                if (!Companion::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteDatabase::class.java,
                        "note_database")
                        //.fallbackToDestructiveMigration()
                        //.allowMainThreadQueries()
                        //.addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE note ADD COLUMN date String")
            }
        }

    }


    //@RenameTable(fromTableName = "note_table", toTableName = "new_note_table")
   // class MyAutoMigration : AutoMigrationSpec

}