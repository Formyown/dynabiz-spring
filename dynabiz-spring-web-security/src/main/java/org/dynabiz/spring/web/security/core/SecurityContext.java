package org.dynabiz.spring.web.security.core;


import org.dynabiz.std.exception.PermissionException;
import org.dynabiz.util.Assert;
import org.dynabiz.web.context.AbstractServiceContext;
import org.dynabiz.web.context.ServiceContextHolder;


import java.util.Objects;
import java.util.Set;



public class SecurityContext extends AbstractServiceContext {

    public SecurityContext() {
    }

    public SecurityContext(String uid, String role, Set<String> permissions) {
        this.uid = uid;
        this.role = role;
        this.permissions = permissions;
    }

    private String uid;
    private String role;
    private Set<String> permissions;

    public static String getCurrentUser(){
        return Assert.notNull(ServiceContextHolder.get(SecurityContext.class),
                PermissionException.NO_PERMISSION).getUid();
    }

    public String getUid() {
        return uid;
    }

    public String getRole() {
        return role;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecurityContext)) return false;
        SecurityContext that = (SecurityContext) o;
        return uid == that.uid &&
                Objects.equals(role, that.role) &&
                Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uid, role, permissions);
    }
}
