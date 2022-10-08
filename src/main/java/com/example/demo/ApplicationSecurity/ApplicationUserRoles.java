package com.example.demo.ApplicationSecurity;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.example.demo.ApplicationSecurity.ApplicationUserPermission.*;

public enum ApplicationUserRoles {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

    private final Set<ApplicationUserPermission> permission;

    ApplicationUserRoles(Set<ApplicationUserPermission> permission) {
        this.permission = permission;
    }
}
