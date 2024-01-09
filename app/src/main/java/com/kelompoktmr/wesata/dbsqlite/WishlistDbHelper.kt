package com.kelompoktmr.wesata.dbsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.kelompoktmr.wesata.dataclass.WishlistData


class WishlistDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val TABLE_NAME = "entry"
    val COLUMN_NAME_TITLE = "title"
    val COLUMN_NAME_SUBTITLE = "subtitle"

    val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${COLUMN_NAME_TITLE} TEXT," +
                "${COLUMN_NAME_SUBTITLE} TEXT)"

    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
    }

    fun insertData(title: String, subtitle: String) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, title)
            put(COLUMN_NAME_SUBTITLE, subtitle)
        }

        val newRowId = db?.insert(TABLE_NAME, null, values)
    }

    fun readData(): ArrayList<WishlistData> {
        val db = readableDatabase
        val projection = arrayOf(BaseColumns._ID, COLUMN_NAME_TITLE, COLUMN_NAME_SUBTITLE)

        val cursor = db.query(
            TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val dataWishlist = ArrayList<WishlistData> ()
        with(cursor) {
            while (moveToNext()) {
                val whislistId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(COLUMN_NAME_TITLE))
                val description = getString(getColumnIndexOrThrow(COLUMN_NAME_SUBTITLE))
                dataWishlist.add(WishlistData(whislistId.toInt(), title, description))
            }
        }
        cursor.close()

        return dataWishlist
    }

    fun deleteData(id: String) {
        val db = writableDatabase
        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(id)
        val deletedRows = db.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun updateData(id: String, title: String, subtitle: String) {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME_TITLE, title)
            put(COLUMN_NAME_SUBTITLE, subtitle)
        }

        val selection = "${BaseColumns._ID} LIKE ?"
        val selectionArgs = arrayOf(id)
        val count = db.update(
            TABLE_NAME,
            values,
            selection,
            selectionArgs)
    }
}