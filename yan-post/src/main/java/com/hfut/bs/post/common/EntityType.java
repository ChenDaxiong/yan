package com.hfut.bs.post.common;

import lombok.Data;

public enum  EntityType {

    POST(1,"post"),

    COMMENT(2,"comment");


    private int entityCode;
    private String entityDesc;

    public final static int ENTITY_POST = 1;
    public final static int ENTITY_COMMENT = 2;

    EntityType(int entityCode, String entityDesc) {
        this.entityCode = entityCode;
        this.entityDesc = entityDesc;
    }

    public String getEntityDesc(){
        return this.entityDesc;
    }



}
