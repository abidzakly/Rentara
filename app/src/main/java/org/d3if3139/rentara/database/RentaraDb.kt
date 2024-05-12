package org.d3if3139.rentara.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import org.d3if3139.rentara.model.Recipe
import org.d3if3139.rentara.model.User

@Database(entities = [Recipe::class, User::class], version = 1, exportSchema = false)
abstract class RentaraDb : RoomDatabase() {

    abstract val dao: RentaraDao

    companion object {

        @Volatile
        private var INSTANCE: RentaraDb? = null

        fun getInstance(context: Context): RentaraDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RentaraDb::class.java,
                        "rentara.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}