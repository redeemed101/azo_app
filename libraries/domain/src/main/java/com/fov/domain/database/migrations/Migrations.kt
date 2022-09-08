package com.fov.domain.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("BEGIN TRANSACTION;")

        database.execSQL("CREATE TABLE IF NOT EXISTS `DownloadedSong` (`dbId` INTEGER, " +
                "`songId` TEXT ,`songName` TEXT,`songPath` TEXT, `artistName` TEXT," +
                " PRIMARY KEY(`dbId`))")
        database.execSQL("CREATE TABLE IF NOT EXISTS `DownloadedAlbum` (`dbId` INTEGER, " +
                "`albumId` TEXT ,`albumName` TEXT,`albumPath` TEXT, `artistName` TEXT," +
                " PRIMARY KEY(`dbId`))")

        database.execSQL("COMMIT;")
    }
}

val MIGRATION_4_5 = object : Migration(4,5) {
    override fun migrate(database: SupportSQLiteDatabase){
        database.execSQL("BEGIN TRANSACTION;")
        // Alter, create, drop, whatever
        database.execSQL("ALTER TABLE `DownloadedSong` ADD COLUMN imagePath TEXT;")

        database.execSQL("ALTER TABLE `DownloadedAlbum` ADD COLUMN imagePath TEXT;")


        database.execSQL("COMMIT;")
    }
}