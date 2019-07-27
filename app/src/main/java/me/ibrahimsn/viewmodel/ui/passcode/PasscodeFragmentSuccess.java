package me.ibrahimsn.viewmodel.ui.passcode;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import me.ibrahimsn.viewmodel.R;
import me.ibrahimsn.viewmodel.base.BaseFragment;
import me.ibrahimsn.viewmodel.util.ViewModelFactory;

public class PasscodeFragmentSuccess extends DialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.screen_success,container,false);

        Bundle bundle=this.getArguments();

        TextView textView=view.findViewById(R.id.sucessDescription);

        Button button=view.findViewById(R.id.button);

        textView.setText(bundle.getString("title"));


        button.setOnClickListener(view1 -> {
            FragmentCallback callback=(FragmentCallback)getTargetFragment();
            callback.callBack();
        });

        return view;
    }
}
