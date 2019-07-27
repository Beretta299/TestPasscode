package me.ibrahimsn.viewmodel.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PassCode {

    public PassCode(long id, String passcode){
        this.id=0;
        this.passcode=passcode;
    }


    @PrimaryKey(autoGenerate = true)
    public long id;
    public String passcode;
}
