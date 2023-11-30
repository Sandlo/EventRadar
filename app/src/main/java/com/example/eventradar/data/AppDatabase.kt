package com.example.eventradar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.eventradar.data.dao.EventDao
import com.example.eventradar.data.dao.TicketDao
import com.example.eventradar.data.entities.Event
import com.example.eventradar.data.entities.Ticket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [Ticket::class, Event::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun eventDao(): EventDao

    companion object {
        private const val DATABASE_NAME = "event.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            GlobalScope.launch {
                                getInstance(context).ticketDao().insertAll(
                                    Ticket(1, 1, 1, 0),
                                    Ticket(2, 2, 1, 0),
                                    Ticket(3, 3, 1, 0)
                                )
                                getInstance(context).eventDao().insertAll(
                                    Event(1, 1, 5.0, "Test 1", 0, 0, 1, "Test", ""),
                                    Event(2, 1, 4.0, "Test 2", 0, 0, 1, "Test test", ""),
                                    Event(3, 1, 3.0, "Test 3", 0, 0, 1, "Test test test", "")
                                )
                            }
                        }
                    }
                )
                .build()
        }
    }
}