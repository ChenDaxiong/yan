package com.hfut.bs.post.common;

public enum EntityTypeEnum {

    POST(1,"post"),

    COMMENT(2,"comment");


    private int entityCode;
    private String entityDesc;

    public final static int ENTITY_POST = 1;
    public final static int ENTITY_COMMENT = 2;

    EntityTypeEnum(int entityCode, String entityDesc) {
        this.entityCode = entityCode;
        this.entityDesc = entityDesc;
    }

    public static String getValueByCode(int entityCode){
        for (EntityTypeEnum type : EntityTypeEnum.values()){
            if (type.entityCode == entityCode){
                return type.entityDesc;
            }
        }
        return null;
    }

    public String getEntityDesc(){
        return this.entityDesc;
    }



}
