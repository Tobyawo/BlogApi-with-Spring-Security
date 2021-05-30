package com.fb2.fb.Security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.fb2.fb.Security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    USER(Sets.newHashSet(USER_POST,USER_LIKE, USER_COMMENT)),
    ADMIN(Sets.newHashSet(ADMIN_DELETE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
