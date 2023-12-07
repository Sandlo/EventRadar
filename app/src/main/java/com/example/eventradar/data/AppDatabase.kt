package com.example.eventradar.data

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.eventradar.R
import com.example.eventradar.activities.MainActivity
import com.example.eventradar.data.dao.AccountDao
import com.example.eventradar.data.dao.AddressDao
import com.example.eventradar.data.dao.EventDao
import com.example.eventradar.data.dao.EventInterestDao
import com.example.eventradar.data.dao.InterestDao
import com.example.eventradar.data.dao.OrganizerDao
import com.example.eventradar.data.dao.ReviewDao
import com.example.eventradar.data.dao.TicketDao
import com.example.eventradar.data.dao.ZipCodeDao
import com.example.eventradar.data.entities.Account
import com.example.eventradar.data.entities.Address
import com.example.eventradar.data.entities.Event
import com.example.eventradar.data.entities.EventInterest
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.data.entities.Organizer
import com.example.eventradar.data.entities.Review
import com.example.eventradar.data.entities.Ticket
import com.example.eventradar.data.entities.ZipCode
import com.example.eventradar.helpers.Base64
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

@Database(
    entities = [
        Account::class,
        Address::class,
        Event::class,
        EventInterest::class,
        Interest::class,
        Organizer::class,
        Review::class,
        Ticket::class,
        ZipCode::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

    abstract fun eventDao(): EventDao

    abstract fun interestDao(): InterestDao

    abstract fun eventInterestDao(): EventInterestDao

    abstract fun reviewDao(): ReviewDao

    abstract fun addressDao(): AddressDao

    abstract fun organizerDao(): OrganizerDao

    abstract fun zipCodeDao(): ZipCodeDao

    abstract fun accountDao(): AccountDao

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
                        @Suppress("MagicNumber", "LongMethod")
                        private suspend fun fillDatabase(database: AppDatabase) {
                            database.apply {
                                ticketDao().insertAll(
                                    Ticket(1, 1, 0),
                                    Ticket(2, 1, 0),
                                    Ticket(3, 1, 0),
                                )
                                eventDao().insertAll(
                                    Event(
                                        1,
                                        5.0,
                                        "Test 1",
                                        1000000000,
                                        0,
                                        1,
                                        "Test",
                                        Base64.getFromAssets(context, "bar.jpg"),
                                    ),
                                    Event(
                                        1,
                                        4.0,
                                        "Test 2",
                                        2000000000,
                                        0,
                                        1,
                                        "Test test",
                                        Base64.getFromAssets(context, "club.jpg"),
                                    ),
                                    Event(
                                        1,
                                        3.0,
                                        "Test 3",
                                        3000000000,
                                        0,
                                        1,
                                        "Test test test",
                                        Base64.getFromAssets(context, "theater.jpg"),
                                    ),
                                )
                                interestDao().insertAll(
                                    Interest("Interesse 1"),
                                    Interest("Interesse 2"),
                                    Interest("Interesse 3"),
                                )
                                eventInterestDao().insertAll(
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
                                reviewDao().insertAll(
                                    Review(1, 1, "", 2.5f, 1000000000),
                                    Review(2, 1, "", 3.5f, 2000000000),
                                    Review(3, 1, "", 4.5f, 3000000000),
                                )
                                organizerDao().insertAll(
                                    Organizer("Luca"),
                                )
                                zipCodeDao().insertAll(
                                    ZipCode("76133", "Karlsruhe"),
                                )
                                addressDao().insertAll(
                                    Address("Moltkestraße", "76133", "30"),
                                )
                                accountDao().insertAll(
                                    Account(
                                        "maxmustermann@gmx.de",
                                        "0800 897378423",
                                        BCrypt.hashpw("123456789", BCrypt.gensalt()),
                                    ),
                                )
                            }
                        }

                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                fillDatabase(getInstance(context))
                                ContextCompat.getMainExecutor(context).execute {
                                    MaterialAlertDialogBuilder(context).setTitle(R.string.dummy_database)
                                        .setMessage(R.string.dummy_database_summary)
                                        .setPositiveButton(R.string.ok) { _, _ ->
                                            context.startActivity(
                                                Intent(
                                                    context,
                                                    MainActivity::class.java,
                                                ).addFlags(
                                                    Intent.FLAG_ACTIVITY_NEW_TASK or
                                                        Intent.FLAG_ACTIVITY_CLEAR_TASK,
                                                ),
                                            )
                                        }
                                        .show()
                                }
                            }
                        }
                    },
                )
                .build()
        }
    }
}
