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
import com.example.eventradar.data.dao.AccountInterestDao
import com.example.eventradar.data.dao.AddressDao
import com.example.eventradar.data.dao.EventDao
import com.example.eventradar.data.dao.EventInterestDao
import com.example.eventradar.data.dao.InterestDao
import com.example.eventradar.data.dao.OrganizerDao
import com.example.eventradar.data.dao.ReviewDao
import com.example.eventradar.data.dao.TicketDao
import com.example.eventradar.data.dao.UserDao
import com.example.eventradar.data.dao.ZipCodeDao
import com.example.eventradar.data.entities.Account
import com.example.eventradar.data.entities.AccountInterest
import com.example.eventradar.data.entities.Address
import com.example.eventradar.data.entities.Event
import com.example.eventradar.data.entities.EventInterest
import com.example.eventradar.data.entities.Interest
import com.example.eventradar.data.entities.Organizer
import com.example.eventradar.data.entities.Review
import com.example.eventradar.data.entities.Ticket
import com.example.eventradar.data.entities.User
import com.example.eventradar.data.entities.ZipCode
import com.example.eventradar.helpers.Base64
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

/**
 * Die Klasse AppDatabase repräsentiert die Room-Datenbank für die Event Radar-Anwendung.
 * Sie definiert die Datenbankentitäten, die Version und stellt Datenbankoperationen bereit.
 */
@Database(
    entities = [
        Account::class,
        AccountInterest::class,
        Address::class,
        Event::class,
        EventInterest::class,
        Interest::class,
        Organizer::class,
        Review::class,
        Ticket::class,
        User::class,
        ZipCode::class,
    ],
    version = 1,
    exportSchema = false,
)
@Suppress("TooManyFunctions")
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

    /**
     * DAO zum Zugriff auf AccountInterest-Daten.
     */
    abstract fun accountInterestDao(): AccountInterestDao

    /**
     * DAO zum Zugriff auf User-Daten.
     */
    abstract fun userDao(): UserDao

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
                                        29.00,
                                        "Lucas Kochabend",
                                        1706227200000,
                                        1706211000000,
                                        1,
                                        "Alle mögen gutes Essen, deshalb werden Sie " +
                                            "auch Luca mögen!",
                                        Base64.getFromAssets(context, "kochen.jpg"),
                                    ),
                                    Event(
                                        1,
                                        8.50,
                                        "Domis Dampflockausstellung",
                                        1706299900000,
                                        1706300000000,
                                        1,
                                        "Unser Dampflockexperte Domi lädt zu einem " +
                                            "sinnlichen Abentuer in seine Privataussletung ein.",
                                        Base64.getFromAssets(context, "bahn.jpg"),
                                    ),
                                    Event(
                                        1,
                                        11.50,
                                        "Musical: Der große Joel alleine im Wald?",
                                        1706299900000,
                                        1706300000000,
                                        1,
                                        "Eine unvergessleicher Abend voller Tanz und " +
                                            "Stimmung",
                                        Base64.getFromAssets(context, "musical.jpg"),
                                    ),
                                    Event(
                                        1,
                                        8.50,
                                        "Tanz mit Tim",
                                        1706299900000,
                                        1706300000000,
                                        1,
                                        "Wer sein Tanzbein mal wieder so richtig " +
                                            "schwingen will, darf diesen Event nicht " +
                                            "verpassen!",
                                        Base64.getFromAssets(context, "tanz.jpg"),
                                    ),
                                    Event(
                                        1,
                                        8.50,
                                        "Silvesterparty",
                                        1706299900000,
                                        1704088800000,
                                        1,
                                        "Feier mit uns gemeinsam in das neue Jahr!",
                                        Base64.getFromAssets(context, "silvester.jpg"),
                                    ),
                                    Event(
                                        1,
                                        12.00,
                                        "Zeitreise: Kunst durch die Jahrhunderte",
                                        1714557600000,
                                        1719770400000,
                                        1,
                                        "Eine Ausstellung, die Kunstwerke aus verschiedenen " +
                                                "Epochen präsentiert, von der Renaissance bis zur modernen Kunst.",
                                        Base64.getFromAssets(context, "zeitreise.jpg"),
                                    ),
                                    Event(
                                        1,
                                        10.00,
                                        "Fotografie Heute: Eine globale Perspektive",
                                        1721037600000,
                                        1723744800000,
                                        1,
                                        "Eine Fotografie Ausstellung, die Werke von zeitgenössischen " +
                                                "Fotografen aus der ganzen Welt zeigt.",
                                        Base64.getFromAssets(context, "fotografie.jpg"),
                                    ),
                                    Event(
                                        1,
                                        15.00,
                                        "Design des 21. Jahrhunderts",
                                        1725184800000,
                                        1727719200000,
                                        1,
                                        "Eine Ausstellung, die innovative Designkonzepte aus den Bereichen Mode, " +
                                                "Möbel, Grafik und Industriedesign präsentiert.",
                                        Base64.getFromAssets(context, "design.jpg"),
                                    ),
                                    Event(
                                        1,
                                        18.00,
                                        "Natur in der Kunst",
                                        17277768000000,
                                        17303976000000,
                                        1,
                                        "Eine Ausstellung, die Kunstwerke präsentiert, die die " +
                                                "Schönheit und Vielfalt der Natur darstellen.",
                                        Base64.getFromAssets(context, "natur.jpg"),
                                    ),
                                    Event(
                                        1,
                                        20.00,
                                        "Lichter der Welt",
                                        1733076000000,
                                        1735682400000,
                                        1,
                                        "Ein Festival, das Lichtinstallationen von internationalen " +
                                                "Künstlern in der ganzen Stadt präsentiert. ",
                                        Base64.getFromAssets(context, "lichter.jpg"),
                                    ),
                                    Event(
                                        1,
                                        35.00,
                                        "Rhythmus der Nationen",
                                        1719835200000,
                                        1722463200000,
                                        1,
                                        "Ein Musikfestival, das Bands und Musiker aus verschiedenen " +
                                                "Ländern und Kulturen zusammenbringt. ",
                                        Base64.getFromAssets(context, "rhythmus.jpg"),
                                    ),
                                    Event(
                                        1,
                                        10.00,
                                        "Straßenkunst Spektakel",
                                        1722506400000,
                                        1725141600000,
                                        1,
                                        "Ein Festival, das Straßenkünstler, Akrobaten und Performer " +
                                                "aus der ganzen Welt in einer Stadt versammelt. ",
                                        Base64.getFromAssets(context, "straßenkunst.jpg"),
                                    ),
                                    Event(
                                        1,
                                        15.00,
                                        "Filmnächte unter den Sternen",
                                        1725220800000,
                                        1727740740000,
                                        1,
                                        "Ein Open-Air-Filmfestival, das klassische und zeitgenössische " +
                                                "Filme unter dem Sternenhimmel zeigt. ",
                                        Base64.getFromAssets(context, "filmnächte.jpg"),
                                    ),
                                    Event(
                                        1,
                                        25.00,
                                        "Elektronische Ekstase",
                                        1717279200000,
                                        1717300800000,
                                        1,
                                        "Eine Party, die sich auf elektronische Musik konzentriert " +
                                                "und Top-DJs aus der ganzen Welt einlädt. ",
                                        Base64.getFromAssets(context, "elektronische.jpg"),
                                    ),
                                    Event(
                                        1,
                                        20.00,
                                        "Retro Revival Nacht",
                                        1721080800000,
                                        1721102400000,
                                        1,
                                        "Eine Themenparty, die die Musik und Mode der 80er und 90er Jahre feiert.",
                                        Base64.getFromAssets(context, "retro.jpg"),
                                    ),
                                    Event(
                                        1,
                                        30.00,
                                        "Tanz unter den Sternen",
                                        1724184000000,
                                        1724205600000,
                                        1,
                                        "Eine Open-Air-Party im Mannheimer Stadtpark mit Live-Musik und Tanzflächen. ",
                                        Base64.getFromAssets(context, "tanzunter.jpg"),
                                    ),
                                    Event(
                                        1,
                                        15.00,
                                        "Club der Kulturen",
                                        1726005600000,
                                        1726027200000,
                                        1,
                                        "Eine Partyreihe, die Musikstile aus verschiedenen " +
                                                "Kulturen und Ländern präsentiert.  ",
                                        Base64.getFromAssets(context, "clubder.jpg"),
                                    ),
                                )

                                interestDao().insertAll(
                                    Interest("Kultur", Base64.getFromAssets(context, "kultur.jpg")),
                                    Interest("Tanzen", Base64.getFromAssets(context, "musicalm.jpg")),
                                    Interest(
                                        "Alkohol",
                                        Base64.getFromAssets(context, "bar.jpg"),
                                    ),
                                    Interest("Essen", Base64.getFromAssets(context, "essen.jpg")),
                                    Interest("Party", Base64.getFromAssets(context, "club.jpg")),
                                )
                                eventInterestDao().insertAll(
                                    EventInterest(1, 2),
                                    EventInterest(1, 3),
                                    EventInterest(2, 4),
                                    EventInterest(2, 3),
                                    EventInterest(3, 1),
                                    EventInterest(3, 2),
                                    EventInterest(4, 2),
                                    EventInterest(4, 3),
                                    EventInterest(5, 3),
                                    EventInterest(5, 2),
                                    EventInterest(5, 4),
                                    EventInterest(5, 5),
                                    EventInterest(6, 1),
                                    EventInterest(7, 1),
                                    EventInterest(8, 1),
                                    EventInterest(9, 1),
                                    EventInterest(10, 2),
                                    EventInterest(10, 3),
                                    EventInterest(10, 5),
                                    EventInterest(11, 2),
                                    EventInterest(11, 3),
                                    EventInterest(11, 5),
                                    EventInterest(12, 2),
                                    EventInterest(12, 3),
                                    EventInterest(12, 5),
                                    EventInterest(13, 2),
                                    EventInterest(13, 3),
                                    EventInterest(13, 5),
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
                                accountInterestDao().insertAll(
                                    AccountInterest(1, 1),
                                    AccountInterest(1, 2),
                                    AccountInterest(1, 3),
                                )
                            }
                        }

                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                fillDatabase(getInstance(context))
                            }
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
                    },
                )
                .build()
        }
    }
}
