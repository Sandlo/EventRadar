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
import java.util.Calendar
import java.util.TimeZone

fun getFixedDateInMillis(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0): Long {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getTimeZone("CET")
    calendar.set(year, month - 1, day, hour, minute, second)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

/**
 * Die Klasse AppDatabase repräsentiert die Room-Datenbank für die Event Radar-Anwendung.
 * Sie definiert die Datenbankentitäten, die Version und stellt Datenbankoperationen bereit.
 */
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
    /**
     * DAO zum Zugriff auf Ticket-Daten.
     */
    abstract fun ticketDao(): TicketDao

    /**
     * DAO zum Zugriff auf Event-Daten.
     */
    abstract fun eventDao(): EventDao

    /**
     * DAO zum Zugriff auf Interessen-Daten.
     */
    abstract fun interestDao(): InterestDao

    /**
     * DAO zum Zugriff auf EventInterest-Daten.
     */
    abstract fun eventInterestDao(): EventInterestDao

    /**
     * DAO zum Zugriff auf Review-Daten.
     */
    abstract fun reviewDao(): ReviewDao

    /**
     * DAO zum Zugriff auf Address-Daten.
     */
    abstract fun addressDao(): AddressDao

    /**
     * DAO zum Zugriff auf Organizer-Daten.
     */
    abstract fun organizerDao(): OrganizerDao

    /**
     * DAO zum Zugriff auf ZipCode-Daten.
     */
    abstract fun zipCodeDao(): ZipCodeDao

    /**
     * DAO zum Zugriff auf Account-Daten.
     */
    abstract fun accountDao(): AccountDao

    companion object {
        private const val DATABASE_NAME = "event.db"

        @Volatile
        private var instance: AppDatabase? = null

        /**
         * Holen Sie eine Instanz von AppDatabase mit einem bereitgestellten Kontext.
         *
         * @param context Der Anwendungskontext.
         * @return Eine Instanz von AppDatabase.
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        /**
         * Erstellen Sie die Room-Datenbank und initialisieren Sie sie mit Dummy-Daten.
         *
         * @param context Der Anwendungskontext.
         * @return Eine Instanz der erstellten AppDatabase.
         */
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
                                    Ticket(4, 1, 1706299000000),
                                    Ticket(5, 1, 1706297700000),
                                    Ticket(6, 1, 1706199000000),
                                    Ticket(7, 1, 1705999000000),
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
                                    Event(
                                        1,
                                        29.00,
                                        "Lucas Kochabend",
                                        1706227200000,
                                        1706211000000,
                                        1,
                                        "Alle mögen gutes Essen, deshalb werden Sie auch Luca mögen!",
                                        Base64.getFromAssets(context, "lucas_kochen.jpg"),
                                    ),
                                    Event(
                                        1,
                                        8.50,
                                        "Domis Dampflockausstellung",
                                        1706299900000,
                                        1706300000000,
                                        1,
                                        "Unser Dampflockexperte Domi lädt zu einem sinnlichen Abentuer in seine Privataussletung ein.",
                                        Base64.getFromAssets(context, "dampflock.jpg"),
                                    ),
                                    Event(
                                        1,
                                        11.50,
                                        "Musical: Der große Joel alleine im Wald?",
                                        1706299900000,
                                        1706300000000,
                                        1,
                                        "Eine unvergessleicher Abend voller Tanz und Stimmung",
                                        Base64.getFromAssets(context, "musical.jpg"),
                                    ),
                                    Event(
                                        1,
                                        8.50,
                                        "Tanz mit Tim",
                                        1706299900000,
                                        1706300000000,
                                        1,
                                        "Wer sein Tanzbein mal wieder so richtig schwingen will, darf diesen Event nicht verpassen!",
                                        Base64.getFromAssets(context, "tanz.jpg"),
                                    ),
                                )
                                interestDao().insertAll(
                                    Interest("Kultur"),
                                    Interest("Tanzen"),
                                    Interest("Alkohol"),
                                    Interest("Essen"),
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
                                    EventInterest(4, 2),
                                    EventInterest(4, 3),
                                    EventInterest(5, 4),
                                    EventInterest(5, 3),
                                    EventInterest(7, 2),
                                    EventInterest(7, 3),
                                )
                                reviewDao().insertAll(
                                    Review(1, 1, "", 2.5f, 1000000000),
                                    Review(2, 1, "", 3.5f, 2000000000),
                                    Review(3, 1, "", 4.5f, 3000000000),
                                    Review(4, 1, "", 5.0f, 2090000002),
                                    Review(5, 1, "", 4.5f, 2440000002),
                                    Review(6, 1, "", 4.0f, 2570000000),
                                    Review(7, 1, "", 1.5f, 2650000000),
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
