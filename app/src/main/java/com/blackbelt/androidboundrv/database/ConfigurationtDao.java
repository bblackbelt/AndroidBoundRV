package com.blackbelt.androidboundrv.database;

import com.blackbelt.androidboundrv.api.model.Configuration;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ConfigurationtDao {

    @Query("SELECT * FROM Configuration")
    Flowable<List<Configuration>> getConfiguration();

    @Update
    void updateList(Configuration configuration);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Configuration configuration);

    @Delete
    void deleteList(Configuration configuration);
}
