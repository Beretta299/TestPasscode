package me.ibrahimsn.viewmodel.base;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import me.ibrahimsn.viewmodel.di.component.ApplicationComponent;
import me.ibrahimsn.viewmodel.di.component.DaggerApplicationComponent;
import me.ibrahimsn.viewmodel.di.module.DatabaseModule;

public class BaseApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent component = DaggerApplicationComponent.builder().setDBModule(new DatabaseModule(getApplicationContext())).application(this).build();
        component.inject(this);

        return component;
    }
}
