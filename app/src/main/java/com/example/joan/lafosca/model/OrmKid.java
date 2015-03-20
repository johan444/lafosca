package com.example.joan.lafosca.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Joan on 19/3/15.
 */

@DatabaseTable(tableName = "Kid")
public class OrmKid {

    @DatabaseField(id=true)
    private String name;

    @DatabaseField
    private int age;

    public OrmKid() {
        // ORMLite needs a no-arg constructor
    }
    public OrmKid(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
