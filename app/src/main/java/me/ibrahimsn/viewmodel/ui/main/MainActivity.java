package me.ibrahimsn.viewmodel.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v14.preference.ListPreferenceDialogFragment;

import javax.inject.Inject;

import me.ibrahimsn.viewmodel.R;
import me.ibrahimsn.viewmodel.base.BaseActivity;
import me.ibrahimsn.viewmodel.ui.passcode.PasscodeFragment;
import me.ibrahimsn.viewmodel.ui.passcode.PasscodeViewModel;
import me.ibrahimsn.viewmodel.util.ViewModelFactory;
import me.ibrahimsn.viewmodel.ui.list.ListFragment;

public class MainActivity extends BaseActivity {



    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    MainActivityViewModel model;
    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        model= ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);

        isPasscodeSet();

//        if(savedInstanceState == null){
//            ListFragment fragment=new ListFragment();
//            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, fragment).commit();
//        }
    }

    private void isPasscodeSet() {
        model.isPasscode().observe(this, passCode -> {
            if(passCode.passcode=="error"){
                getSupportFragmentManager().beginTransaction().add(R.id.screenContainer,new ListFragment()).addToBackStack(null).commit();
            }else {
                Bundle bundle=new Bundle();
                bundle.putBoolean("checkToGo",true);
                PasscodeFragment fragment=new PasscodeFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, fragment).addToBackStack(null).commit();
            }
        });
    }
}
