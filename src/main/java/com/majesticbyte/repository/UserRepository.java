package com.majesticbyte.repository;

import com.majesticbyte.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends CrudRepository<AppUser, Long> {

    List<AppUser> findByUsername(String username);

    Optional<AppUser> findOneByUsername(String username);
}
