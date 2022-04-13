package org.acme.database.temperature;


import org.acme.database.user.UserRepository;

import javax.inject.Singleton;
import java.util.List;

/**
 * To handle call related to temperature database
 * */
@Singleton
public class TemperatureResources {



    public List<TemperatureRepository> getAll(){
        return TemperatureRepository.listAll();
    }

    public List<TemperatureRepository> getData (Long id){
        return TemperatureRepository.findById(id);
    }

    public TemperatureRepository newDir(TemperatureRepository dir){
        TemperatureRepository entity = TemperatureRepository.findById(getId(dir.userId));
        entity.temperatureDir = dir.temperatureDir;
        return entity;
    }

    public boolean checkExist(Long id){
        UserRepository entity = UserRepository.findById(id);
        return  entity.status;
    }

    private Long getId(Long userId){
        List<TemperatureRepository> tempDetails = TemperatureRepository.find( "userId", userId).list() ;
        return tempDetails.get(0).id;
    }

}
