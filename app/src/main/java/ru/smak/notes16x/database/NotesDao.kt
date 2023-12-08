package ru.smak.notes16x.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ru.smak.notes16x.Note
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class NotesDao(context: Context) : SQLiteOpenHelper(context, NOTES_DB_NAME, null, 1) {
    companion object{
        const val NOTES_DB_NAME = "Notes"
        const val NOTES_TABLE_NAME = "Notes"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $NOTES_TABLE_NAME (" +
                "id integer primary key, " +
                "title text, " +
                "`text` text, " +
                "priority integer, " +
                "time integer)"
        db?.apply{
            try {
                beginTransaction()
                execSQL(query)
                setTransactionSuccessful()
            } catch (e: Exception){
                Log.e("DB", e.message.toString())
            } finally {
                endTransaction()
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addNote(note: Note){
        val values = ContentValues()
        values.put("title", note.title)
        values.put("text", note.text)
        values.put("priority", note.priority)
        values.put("time", note.time.toEpochSecond(ZoneOffset.UTC))
        writableDatabase.apply {
            try {
                beginTransaction()
                insert(NOTES_TABLE_NAME, "", values)
                setTransactionSuccessful()
            } catch (e: Exception){
                Log.e("DB", e.message.toString())
            } finally {
                endTransaction()
            }
        }
    }
}