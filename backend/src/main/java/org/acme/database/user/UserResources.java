package org.acme.database.user;


import org.acme.database.temperature.TemperatureRepository;

import javax.inject.Singleton;
import java.util.List;

/**
 * To handle call related to user database
 * */
@Singleton
public class UserResources {

    public  boolean newUser(UserRepository _user){
        UserRepository.persist(_user);
        return _user.isPersistent();
    }

    public List<UserRepository> getDetail (String email){
        return UserRepository.find("email",email).list();
    }

    public boolean checkUser(String email){
        return UserRepository.find("email", email).list().isEmpty();
    }

    public UserRepository activateStatus(Long id, Boolean status){

        UserRepository entity = UserRepository.findById(id);
        entity.status = status;
        return entity;
    }

    public List<UserRepository> getUsers(){
        return UserRepository.listAll();
    }

    public Long getId(String email){
        List<UserRepository> userDetails = this.getDetail(email);
        return userDetails.get(0).id;
    }

    public String getPassword(Long id){
        UserRepository entity = UserRepository.findById(id);
        return  entity.password;
    }

    public String getDir(Long id){
        UserRepository entity = UserRepository.findById(id);
        return entity.dataDir;
    }

    public boolean checkStatus(Long id){
        UserRepository entity = UserRepository.findById(id);
        return  entity.status;
    }
}
