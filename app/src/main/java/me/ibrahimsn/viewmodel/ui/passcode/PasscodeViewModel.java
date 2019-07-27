package me.ibrahimsn.viewmodel.ui.passcode;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.ibrahimsn.viewmodel.data.database.DBInstance;
import me.ibrahimsn.viewmodel.data.database.IDBDao;
import me.ibrahimsn.viewmodel.data.model.PassCode;
import me.ibrahimsn.viewmodel.data.rest.RepoRepository;

public class PasscodeViewModel extends ViewModel {

    @Inject
    IDBDao dao;

    RepoRepository repoRepository;


    private MutableLiveData<PassCode> liveData=new MutableLiveData<>();

    @Inject
    public PasscodeViewModel(RepoRepository repoRepository) {
        this.repoRepository=repoRepository;
    }



    public LiveData<PassCode> getPassCode(){
        loadPasscode();
        return liveData;
    }




    private void loadPasscode(){
        Log.d("TAGG","LOADPASSCODE");
        dao.getPasscode().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<PassCode>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(PassCode passCode) {
                Log.d("TAGG","We loaded passcode"+passCode.passcode);
                liveData.postValue(passCode);
            }

            @Override
            public void onError(Throwable e) {
                liveData.postValue(new PassCode(0,"error"));
            }
        });
    }

    void deletePasscodes(){
        Completable.fromRunnable(() -> dao.deletePasscode()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

    }

    public void insertPassCode(PassCode pass){
        Completable.fromRunnable(() -> {
            dao.deletePasscode();
            dao.insertPasscode(pass);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("TAGG","insert completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAGG","Some error occured"+e);
            }
        });
    }
}
