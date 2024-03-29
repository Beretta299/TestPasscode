package me.ibrahimsn.viewmodel.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import me.ibrahimsn.viewmodel.R;
import me.ibrahimsn.viewmodel.base.BaseFragment;
import me.ibrahimsn.viewmodel.ui.detail.DetailsViewModel;
import me.ibrahimsn.viewmodel.ui.passcode.PasscodeFragment;
import me.ibrahimsn.viewmodel.util.ViewModelFactory;
import me.ibrahimsn.viewmodel.data.model.Repo;
import me.ibrahimsn.viewmodel.ui.detail.DetailsFragment;

public class ListFragment extends BaseFragment implements RepoSelectedListener {

    @BindView(R.id.recyclerView) RecyclerView listView;
    @BindView(R.id.tv_error) TextView errorTextView;
    @BindView(R.id.loading_view) View loadingView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject ViewModelFactory viewModelFactory;
    private ListViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int layoutRes() {
        return R.layout.screen_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.class);

        toolbar.setTitle("Список репозиториев");
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOverflowIcon(getContext().getDrawable(R.drawable.ic_lock_outline_black_24dp));
        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()==R.id.setPassCode){
                Bundle bundle=new Bundle();
                bundle.putBoolean("isPassCodeset",true);
                PasscodeFragment fragment=new PasscodeFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.screenContainer, fragment).addToBackStack(null).commit();
            }else if(item.getItemId()==R.id.unsetPassCode){
                Bundle bundle=new Bundle();
                bundle.putBoolean("checkToGo",true);
                bundle.putBoolean("unsetPasscode",true);
                PasscodeFragment fragment=new PasscodeFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.screenContainer, fragment).addToBackStack(null).commit();
            }
            return false;
        });

        listView.addItemDecoration(new DividerItemDecoration(getBaseActivity(), DividerItemDecoration.VERTICAL));
        listView.setAdapter(new RepoListAdapter(viewModel, this, this));
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        observableViewModel();
    }

    @Override
    public void onRepoSelected(Repo repo) {
        DetailsViewModel detailsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.setSelectedRepo(repo);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.screenContainer, new DetailsFragment())
                .addToBackStack(null).commit();
    }

    private void observableViewModel() {
        viewModel.getRepos().observe(this, repos -> {
            if(repos != null) listView.setVisibility(View.VISIBLE);
        });

        viewModel.getError().observe(this, isError -> {
            if (isError != null) if(isError) {
                errorTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                errorTextView.setText("An Error Occurred While Loading Data!");
            }else {
                errorTextView.setVisibility(View.GONE);
                errorTextView.setText(null);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                if (isLoading) {
                    errorTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
