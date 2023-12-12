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
        val values = createValuesFor(note)
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

    fun editNote(note: Note){
        val values = createValuesFor(note)
        writableDatabase.apply {
            try{
                beginTransaction()
                if (update(NOTES_TABLE_NAME, values, "id=?", arrayOf(note.id.toString())) > 0)
                    setTransactionSuccessful()
                else throw Exception("Не найдена запись для редактирования")
            } catch (e: Exception){
                Log.e("DB", e.message.toString())
            } finally {
                endTransaction()
            }
        }
    }

    fun deleteNote(note: Note){
        writableDatabase.apply {
            try {
                beginTransaction()
                if (delete(NOTES_TABLE_NAME, "id = ?", arrayOf(note.id.toString())) > 0)
                    setTransactionSuccessful()
                else throw Exception("Не найдена заметка для удаления")
            } catch (e: Exception){
                Log.e("DB", e.message.toString())
            } finally {
                endTransaction()
            }
        }
    }

    fun getAllNotes(): List<Note>{
        val notes: MutableList<Note> = mutableListOf()
        readableDatabase.run {
            try {
                beginTransaction()
                with(
                    query(
                        NOTES_TABLE_NAME,
                        arrayOf("id", "title", "text", "priority", "time"),
                        null,
                        null,
                        null,
                        null,
                        "priority desc, time desc"
                    )
                ) {
                    while (moveToNext()) {
                        notes.add(
                            Note(
                                id = getInt(0),
                                title = getString(1),
                                text = getString(2),
                                priority = getInt(3),
                                time = LocalDateTime.ofEpochSecond(
                                    getInt(4).toLong(),
                                    0,
                                    ZoneOffset.UTC
                                )
                            )
                        )
                    }
                    close()
                }
                setTransactionSuccessful()
            } catch (e: Exception) {
                Log.e("DB", e.message.toString())
            } finally {
                endTransaction()
            }
        }
        return notes
    }

    private fun createValuesFor(note: Note) = ContentValues().apply {
        put("title", note.title)
        put("text", note.text)
        put("priority", note.priority)
        put("time", note.time.toEpochSecond(ZoneOffset.UTC))
    }


}