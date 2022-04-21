package org.azhar.dbmanager;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DataRepository extends PanacheEntity {

    @Column(unique = true)
    public String email;

    public String password;

    public boolean status = false;

    public String dataDir;
}
