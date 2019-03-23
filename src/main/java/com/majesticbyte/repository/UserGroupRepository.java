package com.majesticbyte.repository;

import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "groups", path = "groups")
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    @Override
    @PreAuthorize("@securityService.canViewGroup(#id)")
    Optional<UserGroup> findById(@Param("id")Long id);

    @Override
    @PostFilter("@securityService.canViewGroups(filterObject)")
    List<UserGroup> findAll();

    List<UserGroup> findByName(String name);

    List<UserGroup> findByNameAndMembersIn(String name, List<AppUser> members);

    List<UserGroup> findByNameContainingAndMembersIn(String name, List<AppUser> members);

}
