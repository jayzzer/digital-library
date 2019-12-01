package com.pupsiki.digitallibrary.repositories;

import com.pupsiki.digitallibrary.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
