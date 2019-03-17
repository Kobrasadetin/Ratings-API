package com.majesticbyte.repository;

import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "groups", path = "groups")
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {

    List<UserGroup> findByName(String name);

    List<UserGroup> findByNameAndMembersIn(String name, List<AppUser> members);

    List<UserGroup> findByNameContainingAndMembersIn(String name, List<AppUser> members);

}
