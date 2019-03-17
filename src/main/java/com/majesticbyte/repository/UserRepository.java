package com.majesticbyte.repository;

import com.majesticbyte.model.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<AppUser, Long> {

    List<AppUser> findByUsername(String boo);
}
