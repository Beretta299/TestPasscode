package me.ibrahimsn.viewmodel.ui.passcode;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.ibrahimsn.viewmodel.R;
import me.ibrahimsn.viewmodel.base.BaseFragment;
import me.ibrahimsn.viewmodel.data.model.PassCode;
import me.ibrahimsn.viewmodel.ui.detail.DetailsViewModel;
import me.ibrahimsn.viewmodel.ui.list.ListFragment;
import me.ibrahimsn.viewmodel.util.ViewModelFactory;

public class PasscodeFragment extends BaseFragment implements TextWatcher,View.OnClickListener,FragmentCallback {

    @BindView(R.id.pass1) EditText pass1;
    @BindView(R.id.pass2) EditText pass2;
    @BindView(R.id.pass3) EditText pass3;
    @BindView(R.id.pass4) EditText pass4;
    @BindView(R.id.firstTV) TextView tv1;
    @BindView(R.id.secondTV) TextView tv2;
    @BindView(R.id.thirdTV) TextView tv3;
    @BindView(R.id.fourthTV) TextView tv4;
    @BindView(R.id.fifthTV) TextView tv5;
    @BindView(R.id.sixthTV) TextView tv6;
    @BindView(R.id.seventhTV) TextView tv7;
    @BindView(R.id.eighthTV) TextView tv8;
    @BindView(R.id.ninthTV) TextView tv9;
    @BindView(R.id.nullTV) TextView tv0;
    @BindView(R.id.deleteTV) ImageView deleteButton;
    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.titleTextView) TextView titleTextView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    ArrayList<EditText> list=new ArrayList<>();

    PasscodeFragmentSuccess fragmentSuccess;
    StringBuilder pass=new StringBuilder();

    int position=0;

    PassCode code;

    PasscodeViewModel model;
    @Inject ViewModelFactory viewModelFactory;

    String temporaryCode="";

    boolean checkToGo=false;

    boolean unsetPass=false;

    boolean state=false;


    @Override
    protected int layoutRes() {
        return R.layout.screen_passcode;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        Bundle bundle=this.getArguments();
        if(bundle!=null){
            state=bundle.getBoolean("isPassCodeset");
            checkToGo=bundle.getBoolean("checkToGo");
            unsetPass=bundle.getBoolean("unsetPasscode");
        }


        toolbar.setTitle(getString(R.string.passcode_input));
        if(!checkToGo){
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(view1 -> {

                getActivity().onBackPressed();

            });
        }


        model = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(PasscodeViewModel.class);
        list.add(pass1);
        list.add(pass2);
        list.add(pass3);
        list.add(pass4);

        pass1.addTextChangedListener(this);
        pass2.addTextChangedListener(this);
        pass3.addTextChangedListener(this);
        pass4.addTextChangedListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv0.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        //model.deletePasscodes();

        ImageSpan imageHint = new ImageSpan(getContext(), R.drawable.image_hint);
        String blankString = "       ";
        SpannableString spannableString = new SpannableString(blankString);
        spannableString.setSpan(imageHint, 0, blankString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        pass1.setHint(spannableString);
        pass2.setHint(spannableString);
        pass3.setHint(spannableString);
        pass4.setHint(spannableString);
        textView.setVisibility(View.GONE);
        loadPassCode();

        fragmentSuccess=new PasscodeFragmentSuccess();

        for(EditText et:list){
            et.setFocusableInTouchMode(false);
            et.setFocusable(false);
        }





    }




    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        textView.setVisibility(View.GONE);
        pass.delete(0,pass.length());
        for(EditText et:list){
            et.setBackground(getContext().getDrawable(R.drawable.edittext_state_checker));
            pass.append(et.getText());
        }

        if(pass.length()==4){
            if(code!=null&&checkToGo){
                        if(pass.toString().equals(code.passcode)){
                        String title=getString(R.string.passcode_check);
                        if(unsetPass){
                            model.deletePasscodes();
                            title=getString(R.string.new_passcode);
                        }
                        FragmentTransaction manager = getFragmentManager().beginTransaction();
                        fragmentSuccess.setTargetFragment(this,1);
                        Bundle bundle=new Bundle();
                        bundle.putString("title",title);
                        fragmentSuccess.setArguments(bundle);
                        fragmentSuccess.show(manager,"TAGG");
                    }
                    else {
                        setErrors();
                    }
            }else if(code!=null&&state){
                titleTextView.setText(getString(R.string.existing_passcode));
                    if(pass.toString().equals(code.passcode)){
                            Single.timer(200,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Long>() {
                                 @Override
                                 public void onSubscribe(Disposable d) {

                                 }

                                 @Override
                                 public void onSuccess(Long aLong) {
                                     model.deletePasscodes();
                                     titleTextView.setText(getString(R.string.insert_passcode));
                                     clearFields();
                                     pass.delete(0,pass.length());
                                     code=null;
                                     position=0;
                                 }

                                 @Override
                                 public void onError(Throwable e) {

                                 }
                            });
                }else {
                    setErrors();
                }
            }
            if(code==null){
                if(temporaryCode.equals("")){
                    Single.timer(200,TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(Long aLong) {
                            temporaryCode=pass.toString();
                            clearFields();
                            //Because on click callback works after textchanged, so we make position -1 and in onclick it makes it 0
                            position=0;
                            titleTextView.setText(getContext().getResources().getString(R.string.repeat_passcode));
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

                }else if(pass.toString().equals(temporaryCode)){
                    model.insertPassCode(new PassCode(0,pass.toString()));
                    FragmentTransaction manager = getFragmentManager().beginTransaction();
                    fragmentSuccess.setTargetFragment(this,1);
                    Bundle bundle=new Bundle();
                    bundle.putString("title",getString(R.string.new_passcode));
                    fragmentSuccess.setArguments(bundle);
                    fragmentSuccess.show(manager,"TAGG");
                }else {
                    setErrors();
                }
            }
        }




    }

    private void clearFields() {
        for(int i=0;i<=list.size()-1;i++) {
            list.get(i).setText("");
            list.get(i).setFocusableInTouchMode(false);
            list.get(i).setFocusable(false);
        }

    }

    void setErrors(){
        for (EditText et:list){
            et.setBackground(getContext().getDrawable(R.drawable.edittext_state_wrong));
        }
        Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
        textView.setVisibility(View.VISIBLE);
    }

    private void loadPassCode(){
        model.getPassCode().observe(this,passCode -> {
            if(passCode.passcode!="error"){
                code=passCode;
                if(checkToGo||state){
                    titleTextView.setText(getString(R.string.existing_passcode));
                }
            }else {
                titleTextView.setText(getString(R.string.insert_passcode));
            }
        });
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==tv1.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            Log.d("TAGG","SOME");
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv2.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv3.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv4.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv5.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv6.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv7.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv8.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv9.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==tv0.getId()){
            TextView tvv=(TextView)view;
            list.get(position).setFocusableInTouchMode(true);
            list.get(position).setFocusable(true);
            list.get(position).requestFocus();
            list.get(position).setText(tvv.getText().toString());
            position++;
            if(position>=list.size()) position=list.size()-1;
        }else if(view.getId()==deleteButton.getId()){
            if(list.get(position).getText().toString().equals("")){
                position--;
                if(position<0) position=0;
                list.get(position).setText("");
                list.get(position).setFocusable(false);
                if(position>0){
                list.get(position-1).setFocusable(true);
                list.get(position-1).requestFocus();
                }
                Log.d("TAGG",position+"");
                return;
            }
                list.get(position).setText("");
                list.get(position).setFocusable(false);
                position--;
                if(position<0) position=0;
                list.get(position).setFocusable(true);
                list.get(position).requestFocus();
        }
        Log.d("TAGG",position+"");
    }

    @Override
    public void callBack() {
        fragmentSuccess.dismiss();
        getFragmentManager().beginTransaction().replace(R.id.screenContainer, new ListFragment()).commit();
    }
}
