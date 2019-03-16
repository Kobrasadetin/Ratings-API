package com.majesticbyte.repository;

import com.majesticbyte.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
