package com.revature.sfadapter.data.services;

import com.revature.sfadapter.data.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * Created by August Duet on 11/21/2016.
 */
public abstract class DaoService<T, ID extends Serializable>{

    private BaseRepository<T, ID> repo;

    @Autowired
    public void setRepo(BaseRepository<T, ID> repo){
        Assert.notNull(repo);
        this.repo = repo;
    }

    public BaseRepository<T, ID> getRepo(){
        return repo;
    }


    public T getOne(ID id){
        return repo.findOne(id);
    }

    public List<T> getAll(ID id){
        return repo.findAll();
    }

    public T saveOne(T item){
        return repo.save(item);
    }

    public List<T> saveAll(List<T> items){
        return repo.save(items);
    }
}
