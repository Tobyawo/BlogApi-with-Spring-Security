package com.fb2.fb.Security;

public enum ApplicationUserPermission {
    USER_POST("user:post"),
    USER_COMMENT("user:comment"),
    USER_LIKE("user:like"),
    ADMIN_DELETE("admin:delete"),
    ADMIN_UPDATE("admin:update");

    private final String permission;



    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
