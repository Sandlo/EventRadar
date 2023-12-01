package com.example.eventradar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.eventradar.data.dao.EventDao
import com.example.eventradar.data.dao.EventInterestDao
import com.example.eventradar.data.dao.InterestDao
import com.example.eventradar.data.dao.ReviewDao
import com.example.eventradar.data.dao.TicketDao
import com.example.eventradar.data.entities.Event
import com.example.eventradar.data.entities.EventInterest
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.data.entities.Review
import com.example.eventradar.data.entities.Ticket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Ticket::class,
        Event::class,
        Interest::class,
        EventInterest::class,
        Review::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun eventDao(): EventDao
    abstract fun interestDao(): InterestDao
    abstract fun eventInterestDao(): EventInterestDao
    abstract fun reviewDao(): ReviewDao

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
                            CoroutineScope(Dispatchers.IO).launch {
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
                                getInstance(context).interestDao().insertAll(
                                    Interest(1, "Interesse 1"),
                                    Interest(2, "Interesse 2"),
                                    Interest(3, "Interesse 3")
                                )
                                getInstance(context).eventInterestDao().insertAll(
                                    EventInterest(1, 1),
                                    EventInterest(2, 1),
                                    EventInterest(3, 1),
                                    EventInterest(1, 2),
                                    EventInterest(2, 2),
                                    EventInterest(3, 2),
                                    EventInterest(1, 3),
                                    EventInterest(2, 3),
                                    EventInterest(3, 3),
                                )
                                getInstance(context).reviewDao().insertAll(
                                    Review(1, 1, 1, "", 2.5f, 0),
                                    Review(2, 2, 1, "", 3.5f, 0),
                                    Review(3, 3, 1, "", 4.5f, 0)
                                )
                            }
                        }
                    }
                )
                .build()
        }
    }
}