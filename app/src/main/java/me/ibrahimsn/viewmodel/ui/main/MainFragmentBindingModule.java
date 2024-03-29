package me.ibrahimsn.viewmodel.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.ibrahimsn.viewmodel.ui.detail.DetailsFragment;
import me.ibrahimsn.viewmodel.ui.list.ListFragment;
import me.ibrahimsn.viewmodel.ui.passcode.PasscodeFragment;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();

    @ContributesAndroidInjector
    abstract PasscodeFragment providePasscodeFragment();


}
