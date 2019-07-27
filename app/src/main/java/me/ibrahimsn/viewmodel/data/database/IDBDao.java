package me.ibrahimsn.viewmodel.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;
import io.reactivex.Single;
import me.ibrahimsn.viewmodel.data.model.PassCode;

@Dao
public interface IDBDao{
    @Query("select * from PassCode limit 1")
    Single<PassCode> getPasscode();

    @Query("delete from PassCode")
    void deletePasscode();

    @Query("update PassCode set passcode=:newCode")
    void updatePassCode(int newCode);

    @Insert
    void insertPasscode(PassCode passCode);


}
