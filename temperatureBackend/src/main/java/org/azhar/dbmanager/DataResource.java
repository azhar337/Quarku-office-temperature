package org.azhar.dbmanager;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class DataResource {

    public  boolean newUser(DataRepository user){
        if (!user.status && user.email != null && user.password != null ){
            DataRepository.persist(user);
            return user.isPersistent();
        }
        return false;
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
        List<DataRepository> userDetail = this.getDetail(email);
        return userDetail.get(0).id;
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

    public String uploadDir(Long id, String dir){
        DataRepository entity = DataRepository.findById(id);


        return dir;
    }


}
