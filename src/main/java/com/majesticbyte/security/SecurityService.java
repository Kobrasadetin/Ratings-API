package com.majesticbyte.security;

import com.majesticbyte.model.UserGroup;

import java.util.List;

public interface SecurityService {

    boolean canViewGroup();

    boolean canViewGroup(Long id);

    boolean canViewGroups(UserGroup userGroup);
}
