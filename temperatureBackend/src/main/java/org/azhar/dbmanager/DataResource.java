package org.azhar.dbmanager;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
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

    public String uploadDir(Long id, String newDir){
        DataRepository entity = DataRepository.findById(id);
        String oldDir = entity.dataDir;
        String updatedDir = oldDir +","+newDir;
        if (updatedDir.length() <= 2000000) {
            DataRepository.update("dataDir = ?1 where id= ?2", updatedDir, id);
            return entity.dataDir;
        }
        return "Over limit";
    }

    public String getSpecificDir (String dir, Long dataId){
        int id = Math.toIntExact(dataId);
        ArrayList<String> targetDir = new ArrayList<>(Arrays.asList(dir.split(",")));
        return String.valueOf(targetDir.get(id));
    }


}
