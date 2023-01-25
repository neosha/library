package ru.library.library.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
public class SoftDelete {

    @Column(name = "is_deleted")
    private String deleted = "N";

    public void setDeleted(){
        this.deleted = "Y";
    }

    public void setDeleted(String value){
        this.deleted = value;
    }

}
