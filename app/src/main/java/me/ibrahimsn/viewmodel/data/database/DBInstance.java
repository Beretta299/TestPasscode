package me.ibrahimsn.viewmodel.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.ibrahimsn.viewmodel.data.model.PassCode;

@Database(entities = {PassCode.class},version = 2)
abstract public class DBInstance extends RoomDatabase {

abstract public IDBDao getDao();

}
