package com.beata.sync.enums;

public enum BeataRoleEnum {
    LAUNCHER("launcher", 0),
    PARTICIPANT("participant", 1)
    ;

    String roleName;
    int id;

    BeataRoleEnum(String roleName, int id) {
        this.roleName = roleName;
        this.id = id;
    }

}
