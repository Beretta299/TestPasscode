package me.ibrahimsn.viewmodel.di.module;

import android.arch.persistence.room.Room;
import android.content.Context;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.ibrahimsn.viewmodel.data.database.*;

@Module
public class DatabaseModule {
    Context context;

    public DatabaseModule(Context context){
        this.context=context;
    }




    @Singleton
    @Provides
    public IDBDao getDao(){
            DBInstance INSTANCE= Room.databaseBuilder(context,DBInstance.class,"mvvm.db")
                        .fallbackToDestructiveMigration()
                    .build();
        return INSTANCE.getDao();
    }


}
