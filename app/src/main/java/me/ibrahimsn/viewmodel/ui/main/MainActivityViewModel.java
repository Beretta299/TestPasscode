package me.ibrahimsn.viewmodel.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.ibrahimsn.viewmodel.data.database.IDBDao;
import me.ibrahimsn.viewmodel.data.model.PassCode;


public class MainActivityViewModel extends ViewModel {
    @Inject
    IDBDao dao;

    @Inject
    MainActivityViewModel(){

    }

    private MutableLiveData<PassCode> liveDate=new MutableLiveData<>();


    public LiveData<PassCode> isPasscode(){
        CompositeDisposable disposeBag=new CompositeDisposable();


    dao.getPasscode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PassCode>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PassCode passCode) {
                        liveDate.postValue(passCode);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveDate.postValue(new PassCode(0,"error"));
                    }
                });
    return liveDate;
    }
}
