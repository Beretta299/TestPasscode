package me.ibrahimsn.viewmodel.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import me.ibrahimsn.viewmodel.data.model.PassCode;
import me.ibrahimsn.viewmodel.ui.list.ListViewModel;
import me.ibrahimsn.viewmodel.ui.detail.DetailsViewModel;
import me.ibrahimsn.viewmodel.di.util.ViewModelKey;
import me.ibrahimsn.viewmodel.ui.main.MainActivityViewModel;
import me.ibrahimsn.viewmodel.ui.passcode.PasscodeViewModel;
import me.ibrahimsn.viewmodel.util.ViewModelFactory;

@Singleton
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindListViewModel(ListViewModel listViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(DetailsViewModel detailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PasscodeViewModel.class)
    abstract ViewModel bindPasscodeViewModel(PasscodeViewModel passcodeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
