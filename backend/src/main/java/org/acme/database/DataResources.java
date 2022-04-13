package org.acme.database;



import javax.inject.Singleton;
import java.util.List;

/**
 * To handle call related to user database
 * */
@Singleton
public class DataResources {

    public  boolean newUser(DataRepository _user){
        DataRepository.persist(_user);
        return _user.isPersistent();
    }

    public List<DataRepository> getDetail (String email){
        return DataRepository.find("email",email).list();
    }

    public boolean checkUser(String email){
        return DataRepository.find("email", email).list().isEmpty();
    }

    public DataRepository activateStatus(Long id, Boolean status){

        DataRepository entity = DataRepository.findById(id);
        entity.status = status;
        return entity;
    }

    public List<DataRepository> getUsers(){
        return DataRepository.listAll();
    }

    public Long getId(String email){
        List<DataRepository> userDetails = this.getDetail(email);
        return userDetails.get(0).id;
    }

    public String getPassword(Long id){
        DataRepository entity = DataRepository.findById(id);
        return  entity.password;
    }

    public String getDir(Long id){
        DataRepository entity = DataRepository.findById(id);
        return entity.dataDir;
    }

    public boolean checkStatus(Long id){
        DataRepository entity = DataRepository.findById(id);
        return  entity.status;
    }
}
