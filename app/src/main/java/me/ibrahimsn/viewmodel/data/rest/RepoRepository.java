package me.ibrahimsn.viewmodel.data.rest;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;
import me.ibrahimsn.viewmodel.data.database.IDBDao;
import me.ibrahimsn.viewmodel.data.model.PassCode;
import me.ibrahimsn.viewmodel.data.model.Repo;

public class RepoRepository {

    private final RepoService repoService;

    @Inject
    IDBDao dao;

    @Inject
    public RepoRepository(RepoService repoService) {
        this.repoService = repoService;
    }

    public Single<List<Repo>> getRepositories() {
        return repoService.getRepositories();
    }

    public Single<Repo> getRepo(String owner, String name) {
        return repoService.getRepo(owner, name);
    }

    public void insertPasscode(PassCode passcode){
        dao.insertPasscode(passcode);
    }

}
