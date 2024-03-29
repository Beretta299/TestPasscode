package me.ibrahimsn.viewmodel.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import me.ibrahimsn.viewmodel.base.BaseApplication;
import me.ibrahimsn.viewmodel.di.module.ActivityBindingModule;
import me.ibrahimsn.viewmodel.di.module.ApplicationModule;
import me.ibrahimsn.viewmodel.di.module.ContextModule;
import me.ibrahimsn.viewmodel.di.module.DatabaseModule;

@Singleton
@Component(modules = {ContextModule.class, ApplicationModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class, DatabaseModule.class})
public interface ApplicationComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder setDBModule(DatabaseModule dbModule);

        ApplicationComponent build();
    }



}