package org.acme.database.temperature;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * To create temperature database
 * */

@Entity
public class TemperatureRepository extends PanacheEntity {

    @Column(unique = true)
    public Long userId;

    public String temperatureDir;
}
