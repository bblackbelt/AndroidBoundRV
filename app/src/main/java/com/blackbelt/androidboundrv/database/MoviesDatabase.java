package com.blackbelt.androidboundrv.database;

import com.blackbelt.androidboundrv.api.model.Configuration;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(version = 1, entities = {Configuration.class})
@TypeConverters(Converters.class)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract ConfigurationtDao getConfigurationDao();
}
